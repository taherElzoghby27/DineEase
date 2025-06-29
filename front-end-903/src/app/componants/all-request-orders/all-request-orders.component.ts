import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../../service/order.service';
import {RequestOrderResponse} from '../../../model/request-order-response';

@Component({
  selector: 'app-all-request-orders',
  templateUrl: './all-request-orders.component.html',
  styleUrls: ['./all-request-orders.component.css']
})
export class AllRequestOrdersComponent implements OnInit {

  constructor(private orderService: OrderService) {
  }

  orders: RequestOrderResponse[] = [];
  messageAr = '';
  messageEn = '';

  ngOnInit(): void {
    this.orderService.getAllRequestOrders().subscribe(
      response => {
        this.orders = response;
      }, errors => {
        this.orders = [];
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

}
