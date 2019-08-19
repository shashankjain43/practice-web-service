import {IFilter} from "./IFilter.interface";
import {UserInputValueFilterType} from "../../FilterType";
/**
 * Created by shruti.aggarwal on 11/4/16.
 */
export class AmountFilter implements IFilter<UserInputValueFilterType>{
  getkey(UserInputFilterType):string {
    return undefined;
  }

  getValue():string {
    return undefined;
  }

}
