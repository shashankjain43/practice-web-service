import {FilterAttribute} from "./FilterAttribute";

'use strict'
export interface FilterType{
  isValidFilter(): boolean;
  format(filterAttribute: FilterAttribute): FilterAttribute;
}

export class DateFilterType implements FilterType{
  filter: FilterAttribute;
  constructor(filter: FilterAttribute){
    this.filter = filter
  }
  isValidFilter(){
    return this.filter.displayValue != value;
  }
  format(filterAttribute:FilterAttribute):FilterAttribute {
    return undefined;
  }

}

export class UserInputValueFilterType implements FilterType{
  isValidFilter(){
    return this.filter.isSelected;
  }
  format(filterAttribute:FilterAttribute):FilterAttribute {
    return undefined;
  }
}

export class UserSelectedFilterType implements FilterType{
  isValidFilter(){
    this.filter.displayValue != value;
  }
  format(filterAttribute:FilterAttribute):FilterAttribute {
    return undefined;
  }

}
