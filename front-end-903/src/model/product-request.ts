import {ProductDetailsResponse} from './product-details-response';

export class ProductRequest {
  id?: number;
  name: string;
  imagePath: string;
  description: string;
  price: number;
  categoryId?: number;
  productDetails?: ProductDetailsResponse;

  constructor(init?: Partial<ProductRequest>) {
    Object.assign(this, init);
  }
}
