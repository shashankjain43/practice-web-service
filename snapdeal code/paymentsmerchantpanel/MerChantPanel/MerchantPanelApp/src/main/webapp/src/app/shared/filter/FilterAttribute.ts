import {FilterType} from "./FilterType";

'use strict';
export class FilterAttribute{
  key: string;
  displayValue: string;
  value: string;
  isSelected: boolean;
  type: FilterType;
  defaultValue:string
}
