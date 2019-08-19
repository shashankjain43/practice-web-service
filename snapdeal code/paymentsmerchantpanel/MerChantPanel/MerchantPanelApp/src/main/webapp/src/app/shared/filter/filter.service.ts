import {IFilterService} from "./filter.interface";
import {FilterAttribute} from "./FilterAttribute";

export class FilterService implements IFilterService {
  getAppliedFilters(filters:FilterAttribute[]):FilterAttribute[] {
    return undefined;
  }
  mergeAppliedFiltersWithSameKey(appliedFilters:FilterAttribute[]):FilterAttribute[] {
    return undefined;
  }
  createAppliedFiltersUrl(appliedFilters:FilterAttribute[]):string {
    return undefined;
  }
}
