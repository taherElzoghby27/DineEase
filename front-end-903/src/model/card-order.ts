import {Product} from './product';

export class CardOrder {

  id: number;
  name: string;
  imagePath: string;
  description: string;
  price: number;
  totalPrice: number;
  quantity: number;

  constructor(product: Product) {
    this.id = product.id;
    this.name = product.name;
    this.imagePath = product.imagePath;
    this.description = product.description;
    this.price = product.price;
    this.totalPrice = product.price;
    this.quantity = 1;
  }
}
