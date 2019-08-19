/**
 * Created by chitra.parihar on 24-12-2015.
 */

export interface ICssStyleService {
  addDisplayElement:(string)=>void;
  removeDisplayElement:(string)=>void;
  setOpacity:(Object, string)=>void;
  focusElement:(Object)=>void;
  addCssClass:(string, string)=>void;
  removeCssClass:(string, string)=>void;
  fadeInElement:(Object)=>void;
  changeText:(Object, string)=>void;
  clearValFromElement:(Object)=>void;
}
'use strict';
/* @ngInject */
export class CssStyleService implements ICssStyleService{
  private $document: angular.IDocumentService
  constructor($document: angular.IDocumentService) {
    this.$document = $document;
  }
      addDisplayElement(id)
      {
          angular.element(this.$document[0].querySelector(id)).css({'display': 'block'});
      }

      removeDisplayElement(id)
      {
          angular.element(this.$document[0].querySelector(id)).css({'display': 'none'});
      }
       setOpacity(element,attrVal)
      {
          angular.element(this.$document[0].querySelector(element)).css({'opacity': attrVal});
      }

      focusElement(element)
      {
          angular.element(this.$document[0].querySelector(element)).focus();
      }


      fadeInElement(element)
      {

          angular.element(this.$document[0].querySelector(element)).fadeIn();
      }

      clearValFromElement(element) {
          angular.element(this.$document[0].querySelector(element)).val('');
      }
       changeText(element,textVal) {
          angular.element(this.$document[0].querySelector(element)).text(textVal);
      }

       removeCssClass(parentClass, classToRemove) {
          angular.element(this.$document[0].querySelector(parentClass)).removeClass(classToRemove);
      }

       addCssClass(parentClass, classToAdd) {
          angular.element(this.$document[0].querySelector(parentClass)).addClass(classToAdd);
      }
  }
