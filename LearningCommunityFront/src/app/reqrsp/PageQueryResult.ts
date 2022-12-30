export class PageQueryResult<T> {
  constructor(public totalPage: number,
              public totalElement: number,
              public data: T[]) {
  }
}
