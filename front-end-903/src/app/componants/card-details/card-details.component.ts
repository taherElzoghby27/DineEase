import {Component, OnInit} from '@angular/core';
import {CardService} from '../../../service/card.service';
import {CardOrder} from '../../../model/card-order';
import {OrderDialogComponent} from '../order-dialog/order-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from '../../../service/auth.service';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit {
  constructor(private cardService: CardService, private dialog: MatDialog, private authService: AuthService) {
  }

  orders: CardOrder[];
  profileComplete = false;
  errorMessage = '';

  ngOnInit(): void {
    this.orders = this.cardService.orders;
    this.getProfile();
  }

  getProfile(): void {
    this.authService.getProfile().subscribe(
      profile => {
        if (profile.accountDetails.age && profile.accountDetails.phoneNumber && profile.accountDetails.phoneNumber) {
          this.profileComplete = true;
        }
      }
    );
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

  showDialogOrder(): void {
    if (!this.profileComplete) {
      this.errorMessage = 'Please Complete Your Profile!';
      return;
    }
    const dialogRef = this.dialog.open(OrderDialogComponent, {
      width: '500px',
      data: {
        orders: this.orders,
      },
      panelClass: 'custom-dialog-container'
    });
    dialogRef.afterClosed().subscribe(
      result => {
        if (result?.success) {
          this.orders = [];
        }
      }
    );
  }

}
