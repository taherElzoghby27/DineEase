export class ProductDetailsResponse {
  id?: number;
  preparationTime: string;
  productCode: string;
  idProduct: number;

  constructor(
    preparationTime: string,
    productCode: string,
    idProduct?: number,
    id?: number // optional parameter at the end
  ) {
    this.id = id;
    this.preparationTime = preparationTime;
    this.productCode = productCode;
    this.idProduct = idProduct;
  }
}
