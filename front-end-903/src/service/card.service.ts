import {Injectable} from '@angular/core';
import {CardOrder} from '../model/card-order';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  orders: CardOrder[];
  totalPrice: number;
  totalOrders: number;

  constructor() {
  }

  addProductOrder(order: CardOrder): void {
    let isExisted = false;
    let existedOrder: CardOrder;
    if (this.orders.length > 0) {
      existedOrder = this.orders.find((cardOrder) => cardOrder.id === order.id);
    }
    isExisted = existedOrder !== undefined;
    if (isExisted) {
      this.updateOrder(existedOrder, true);
    } else {
      this.addOrder(order);
    }
  }

  addOrder(order: CardOrder): void {
    this.orders.push(order);
    this.totalOrders++;
  }

  updateOrder(order: CardOrder, add: boolean): void {
    if (add) {
      order.quantity++;
      this.totalPrice += order.price;
    } else {
      order.quantity--;
      this.totalPrice -= order.price;
    }
  }

  removeOrder(order: CardOrder): void {
  }

}
