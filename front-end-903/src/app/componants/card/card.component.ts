import {Component, OnInit} from '@angular/core';
import {CardOrder} from '../../../model/card-order';
import {CardService} from '../../../service/card.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})

export class CardComponent implements OnInit {
  constructor(private cardService: CardService) {
  }

  orders: CardOrder[];
  totalPrice: number;
  totalOrders: number;

  ngOnInit(): void {
    this.orders = this.cardService.orders;
    this.cardService.totalPrice.subscribe(totalPrice => this.totalPrice = totalPrice);
    this.cardService.totalOrders.subscribe(totalOrders => this.totalOrders = totalOrders);
  }

}
