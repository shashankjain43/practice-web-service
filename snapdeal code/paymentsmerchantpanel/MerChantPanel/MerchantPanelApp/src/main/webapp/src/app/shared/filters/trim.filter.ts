/**
 * Created by shruti.aggarwal on 25/4/16.
 */
export function trimFilter(){
  return function (value){
      if(angular.isString(value)){
        return value;
      }
      return value.replace(/^\s+|\s+$/g, '');
  }
}
