/**
 * Created by shruti.aggarwal on 6/4/16.
 */
export interface IFilter<T> {
  getkey(T): string;
  getValue(T): string;
}
