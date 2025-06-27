import {ProductDetailsResponse} from './product-details-response';

export class Product {
  id: number;
  name: string;
  imagePath: string;
  description: string;
  price: number;
  productDetails: ProductDetailsResponse;
}
