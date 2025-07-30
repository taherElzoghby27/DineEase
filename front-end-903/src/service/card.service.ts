import {Injectable} from '@angular/core';
import {CardOrder} from '../model/card-order';
import {BehaviorSubject, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  orders: CardOrder[] = [];
  //  subject with attributes
  // obserable with functions
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalOrders: Subject<number> = new BehaviorSubject<number>(0);

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
      this.addOrder(order);
    } else {
      this.orders.push(order);
    }
    this.calculateTotals();
  }

  addOrder(order: CardOrder): void {
    order.quantity++;
    order.totalPrice += order.price;
    this.calculateTotals();
  }

  minusOrder(order: CardOrder): void {
    if (order.quantity === 1) {
      return;
    }
    order.quantity--;
    order.totalPrice -= order.price;
    this.calculateTotals();
  }

  removeOrder(order: CardOrder): void {
    const index = this.orders.findIndex(cardOrder => cardOrder.id === order.id);
    if (index > -1) {
      this.orders.splice(index, 1);
    }
    this.calculateTotals();
  }

  calculateTotals(): void {
    let totalElementSize = 0;
    let totalElementPrice = 0;

    for (const temp of this.orders) {
      totalElementSize += temp.quantity;
      totalElementPrice += temp.quantity * temp.price;
    }
    this.totalOrders.next(totalElementSize);
    this.totalPrice.next(totalElementPrice);
  }

  clearOrders(): void {
    this.orders = [];
    this.totalOrders.next(0);
    this.totalPrice.next(0);
  }

}
