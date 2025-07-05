import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../../service/order.service';
import {RequestOrderResponse} from '../../../model/request-order-response';
import {AuthService} from '../../../service/auth.service';
import {MatDialog} from '@angular/material/dialog';
import {UpdateOrderStatusComponent} from '../update-order-status/update-order-status.component';
import {OrderStatusRequest} from '../../../model/order-status-request';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-all-request-orders',
  templateUrl: './all-request-orders.component.html',
  styleUrls: ['./all-request-orders.component.css']
})
export class AllRequestOrdersComponent implements OnInit {

  constructor(private orderService: OrderService,
              private authService: AuthService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
  }

  orders: RequestOrderResponse[] = [];
  messageAr = '';
  messageEn = '';
  newStatus = '';

  ngOnInit(): void {
    this.allRequestOrders();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  allRequestOrders(): void {
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

  updateStatus(order: RequestOrderResponse): void {
    const dialogRef = this.dialog.open(UpdateOrderStatusComponent,
      {
        width: '500px',
        data: {status: order.status},
        panelClass: 'custom-dialog-container'
      });
    dialogRef.afterClosed().subscribe(
      result => {
        this.newStatus = result;
        if (this.newStatus !== '') {
          this.updateStatusOrder(order.id, this.newStatus);
        }
      }
    );
  }

  private updateStatusOrder(id: number, newStatus: string): void {
    const requestOrderStatus = new OrderStatusRequest(id, newStatus);
    this.orderService.updateOrder(requestOrderStatus).subscribe(
      response => {
        const index = this.orders.findIndex(order => order.id === id);
        if (index !== -1) {
          this.orders[index] = {
            ...this.orders[index],
            status: newStatus
          };
        }
        this.snackBar.open('Success', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
      },
      errors => {
        this.messageAr = errors.error.bundleMessage.message_ar;
        this.messageEn = errors.error.bundleMessage.message_en;
        this.snackBar.open(`${this.messageAr}\n${this.messageEn}`, 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
      }
    );
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Pending':
        return 'status-pending';
      case 'Preparing':
        return 'status-preparing';
      case 'Ready':
        return 'status-ready';
      case 'Delivered':
        return 'status-delivered';
      default:
        return 'status-unknown';
    }
  }

}
