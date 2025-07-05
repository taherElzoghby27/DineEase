import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-update-order-status',
  templateUrl: './update-order-status.component.html',
  styleUrls: ['./update-order-status.component.css']
})
export class UpdateOrderStatusComponent implements OnInit {

  statuses: string[] = ['Pending', 'Preparing', 'Ready', 'Delivered'];
  selectedStatus: string | null = null;
  currentOrderStatus: string | null = null;

  constructor(
    public dialogRef: MatDialogRef<UpdateOrderStatusComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
  }

  ngOnInit(): void {
    this.currentOrderStatus = this.data.status;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {
    this.dialogRef.close(this.selectedStatus);
  }

}
