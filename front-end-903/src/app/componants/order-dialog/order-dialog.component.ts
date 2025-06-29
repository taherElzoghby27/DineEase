import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {CardOrder} from '../../../model/card-order';
import {RequestOrder} from '../../../model/request-order';
import {OrderService} from '../../../service/order.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-order-dialog',
  templateUrl: './order-dialog.component.html',
  styleUrls: ['./order-dialog.component.css']
})
export class OrderDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private orderService: OrderService,
              private snackBar: MatSnackBar,
              private dialogRef: MatDialogRef<OrderDialogComponent>,
  ) {
  }

  orders: CardOrder[];
  totalPrice = 0;
  totalNumber = 0;
  productIds: number[] = [];
  accountId = 0;

  ngOnInit(): void {
    if (!this.data || !this.data.orders) {
      return;
    }
    this.orders = this.data.orders;
  }

  takeOrder(): void {
    this.productIds = this.orders.map(order => order.id);
    this.accountId = Number(sessionStorage.getItem('account_id'));

    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.orders.length; ++i) {
      this.totalPrice += this.orders[i].totalPrice;
      this.totalNumber += this.orders[i].quantity;
    }
    if (this.productIds.length === 0 || this.totalPrice === 0 || this.totalNumber === 0 || this.accountId === 0) {
      return;
    }
    const orderTaken = new RequestOrder(
      this.accountId,
      this.totalPrice,
      this.totalNumber,
      this.productIds
    );
    this.orderService.requestOrder(orderTaken).subscribe(
      response => {
        this.snackBar.open('Successfully', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.dialogRef.close({success: true});
      },
      errors => {
        const messageAr = errors.error.bundleMessage.message_ar;
        const messageEn = errors.error.bundleMessage.message_en;
        this.snackBar.open(`${messageAr}\n${messageEn}`,
          'Close', {
            duration: 3000, // milliseconds
            verticalPosition: 'top', // or 'bottom'
            panelClass: ['snackbar-success']
          });
        this.dialogRef.close({success: false});
      }
    );
  }
}
