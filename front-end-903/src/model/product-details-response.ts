export class ProductDetailsResponse {
  id: number;
  preparationTime: string;
  productCode: string;
  idProduct: number;

  constructor(preparationTime: string, productCode: string, idProduct: number) {
    this.preparationTime = preparationTime;
    this.productCode = productCode;
    this.idProduct = idProduct;
  }
}
