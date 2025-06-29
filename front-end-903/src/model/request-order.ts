export class RequestOrder {
  accountId: number;
  totalPrice: number;
  totalNumber: number;
  productsIds: Array<number>;

  constructor(
    accountId: number,
    totalPrice: number,
    totalNumber: number,
    productsIds: Array<number>
  ) {
    this.accountId = accountId;
    this.totalPrice = totalPrice;
    this.totalNumber = totalNumber;
    this.productsIds = productsIds;
  }
}
