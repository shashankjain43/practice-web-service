import {HttpRequestInterceptor} from "./services/httpRequestInterceptor.service";
import {HttpService} from "./services/http.service";
import {StorageService} from "./storage/storage.service";
import {PermissionService} from "./permission/permission.service";
import {GlobalOmnitureService} from "./services/globalomniture.service";
import {CssStyleService} from "./services/cssStyle.service";
import {ACCESS_CONSTANT} from "./constants/access.constant";
import {MENU_CONSTANTS} from "./constants/menu.constant";
import {SmoothScrollService} from "./services/scroll.service";
import {OMNITURE_CONSTANT} from "./constants/omniture.constant";
import {APP_MESSAGE_CONSTANT} from "./constants/message.constant";
import {AllowNumber} from "./directives/allowNumber.directive";
import {CompareTo} from "./directives/compareto.directive";
import {DateGreaterThan} from "./directives/dateValidation.directive";
import {DateLowerThan} from "./directives/dateValidation.directive";
import {DateGreaterThanCurrent} from "./directives/dateValidation.directive";
import {FocuseMe} from "./directives/focusMe.directive";
import {FormValueEmpty} from "./directives/FormValueEmpty.directive";
import {NgMax} from "./directives/ngmax.directive";
import {NgMin} from "./directives/ngMin.directive";
import {HasPermission} from "./permission/permission.directive";
import {ScrollFix} from "./directives/ScrollFix";
import {appDirective} from "./directives/util.directive";
import {BindHtmlCompile} from "./directives/bindHtmlCompile.directive";
import {trimFilter} from "./filters/trim.filter";
import {routeLoadingIndicator} from "./directives/loader/loader.directive";
import {fileUpload} from "./services/fileuploader.service";
import {fileModel} from "./directives/fileModel.directive";
import {tooltipShow} from "./directives/bootstrapElements.directive"


'use strict';
export default angular.module('app.shared', [])
  .service('HttpRequestInterceptor', HttpRequestInterceptor)
  .service('HttpService', HttpService)
  .service('StorageService', StorageService)
  .service('PermissionService', PermissionService)
  .service('GlobalOmnitureService', GlobalOmnitureService)
  .service('CssStyleService', CssStyleService)
  .service('SmoothScrollService', SmoothScrollService)
  .service('fileUpload',fileUpload)

  .directive('allowNumber', AllowNumber)
  .directive('compareTo', CompareTo)
  .directive('dateGreaterThan', DateGreaterThan)
  .directive('dateLowerThan', DateLowerThan)
  .directive('dateGreaterThanCurrent', DateGreaterThanCurrent)
  .directive('focuseMe', FocuseMe)
  .directive('formValueEmpty', FormValueEmpty)
  .directive('ngMax', NgMax)
  .directive('ngMin', NgMin)
  .directive('hasPermission', HasPermission)
  .directive('scrollFix', ScrollFix)
  .directive('appDirective', appDirective.instance)
  .directive('bindHtmlCompile', BindHtmlCompile)
  .directive('routeLoadingIndicator', routeLoadingIndicator)
  .directive('fileModel',fileModel)
  .directive('tooltipShow',tooltipShow)

  .constant('ACCESS_CONSTANT', ACCESS_CONSTANT)
  .constant('MENU_CONSTANTS', MENU_CONSTANTS)
  .constant('Omniture_Constant', OMNITURE_CONSTANT)
  .constant('APP_MESSAGE', APP_MESSAGE_CONSTANT)

  .filter('trim', trimFilter)
  .name;
