import {Component, OnInit} from '@angular/core';
import {CardService} from '../../../service/card.service';
import {CardOrder} from '../../../model/card-order';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit {
  constructor(private cardService: CardService) {
  }

  orders: CardOrder[];

  ngOnInit(): void {
    this.orders = this.cardService.orders;
  }

  deleteOrder(order: CardOrder): void {
    this.cardService.removeOrder(order);
  }

  addOrder(order: CardOrder): void {
    this.cardService.addOrder(order);
  }

  minusOrder(order: CardOrder): void {
    this.cardService.minusOrder(order);
  }

}
