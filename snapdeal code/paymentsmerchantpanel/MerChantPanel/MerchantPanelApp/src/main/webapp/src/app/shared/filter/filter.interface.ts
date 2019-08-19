import {FilterAttribute} from "./FilterAttribute";
'use strict';
export interface IFilterService {
  getAppliedFilters(filters:FilterAttribute[]):FilterAttribute[];
  mergeAppliedFiltersWithSameKey(appliedFilters: FilterAttribute[]): FilterAttribute[];
  createAppliedFiltersUrl(appliedFilters: FilterAttribute[]): string;
}
