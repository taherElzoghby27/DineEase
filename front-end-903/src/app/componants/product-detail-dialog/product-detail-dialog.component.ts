import {Component, Inject, OnInit} from '@angular/core';
import {ProductDetailsService} from '../../../service/product-details.service';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Product} from '../../../model/product';

@Component({
  selector: 'app-product-detail-dialog',
  templateUrl: './product-detail-dialog.component.html',
  styleUrls: ['./product-detail-dialog.component.css']
})
export class ProductDetailDialogComponent implements OnInit {

  constructor(private productDetailsService: ProductDetailsService, @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  isLoading = false;
  product: Product;

  ngOnInit(): void {
    console.log('init');
    console.log(this.data.product.id);
    if (this.data && this.data.product && this.data.product.id) {
      this.product = this.data.product;
      this.getProductDetailsByProductId(this.data.product.id);
    }
  }

  getProductDetailsByProductId(productId: string): void {
    this.isLoading = true;
    this.productDetailsService.getProductDetailsByProductId(productId)
      .subscribe(productDetail => {
        this.isLoading = false;
        this.product.productDetails = productDetail;
        console.log(this.product.productDetails.preparationTime);
        console.log(this.product.name);
      }, errors => {
        this.isLoading = false;
      });
  }
}
