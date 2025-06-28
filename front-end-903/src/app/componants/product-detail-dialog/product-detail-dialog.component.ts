import {Component, Inject, OnInit} from '@angular/core';
import {ProductDetailsService} from '../../../service/product-details.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Product} from '../../../model/product';
import {ProductDetailsResponse} from '../../../model/product-details-response';
import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ProductService} from '../../../service/product.service';

@Component({
  selector: 'app-product-detail-dialog',
  templateUrl: './product-detail-dialog.component.html',
  styleUrls: ['./product-detail-dialog.component.css']
})
export class ProductDetailDialogComponent implements OnInit {

  constructor(private productDetailsService: ProductDetailsService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private activatedRoute: ActivatedRoute,
              private dialogRef: MatDialogRef<ProductDetailDialogComponent>,
              private snackBar: MatSnackBar,
              private productService: ProductService) {
  }

  isLoading = false;
  errorBackend = false;
  product: Product;
  key: string;
  idProductDetails: number;
  oldPreparationTime = '';
  oldProductCode = '';
  preparationTime = '';
  productCode = '';
  preparationTimeError = '';
  productCodeError = '';
  errorMessageAr = '';
  errorMessageEn = '';

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => this.handleActions());
  }

  handleActions(): void {
    if (!this.data || !this.data.key || !this.data.product) {
      return;
    }
    this.product = this.data.product;
    this.key = this.data.key;
    if (this.data.key === 'Show') {
      this.getProductDetailsByProductId(this.data.product.id);
    }
    if (this.data.key === 'Update') {
      this.idProductDetails = this.data.product.productDetails.id;
      this.oldPreparationTime = this.data.product.productDetails.preparationTime;
      this.oldProductCode = this.data.product.productDetails.productCode;
      this.preparationTime = this.data.product.productDetails.preparationTime;
      this.productCode = this.data.product.productDetails.productCode;
    }
  }

  getProductDetailsByProductId(productId: string): void {
    this.isLoading = true;
    this.productDetailsService.getProductDetailsByProductId(productId)
      .subscribe(productDetail => {
        this.isLoading = false;
        this.product.productDetails = productDetail;
      }, errors => {
        this.isLoading = false;
      });
  }


  addProductDetails(): void {
    if (!this.validationAddProductDetails()) {
      return;
    }
    this.isLoading = true;
    const productDetails = new ProductDetailsResponse(
      this.preparationTime, this.productCode, this.product.id, null
    );
    this.productDetailsService.addProductDetails(productDetails).subscribe(
      response => {
        this.product.productDetails = productDetails;
        this.getProductDetailsByProductId(`${this.product.id}`);
        this.errorBackend = false;
        this.isLoading = false;
        this.snackBar.open('Success', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.dialogRef.close();
      }
      , errors => {
        this.errorBackend = true;
        this.isLoading = false;
        this.errorMessageAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  updateProductDetails(): void {
    if (!this.validationAddProductDetails()) {
      return;
    }
    this.isLoading = true;
    const productDetails = new ProductDetailsResponse(
      this.preparationTime, this.productCode, this.product.id, this.idProductDetails
    );
    this.productDetailsService.updateProductDetails(productDetails).subscribe(
      response => {
        this.product.productDetails = productDetails;
        this.errorBackend = false;
        this.isLoading = false;
        this.snackBar.open('Success Updated', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.dialogRef.close();
      }
      ,
      errors => {
        this.errorBackend = true;
        this.isLoading = false;
        this.errorMessageAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  validationAddProductDetails(): boolean {
    if (this.data.key === 'Update') {
      if (this.oldPreparationTime === this.preparationTime && this.productCode === this.oldProductCode) {
        this.errorBackend = false;
        this.preparationTimeError = 'preparation time same as old preparation time';
        this.productCodeError = 'product code same as old product code';
        return false;
      }
    }
    if (!this.preparationTime || this.preparationTime === '') {
      this.errorBackend = false;
      this.preparationTimeError = 'preparation time is required';
      return false;
    }
    if (!this.productCode || this.productCode === '') {
      this.errorBackend = false;
      this.productCodeError = 'Product code is required';
      return false;
    }
    return true;
  }

  deleteProduct(): void {
    this.productService.deleteProductById(`${this.product.id}`).subscribe(response => {
      this.snackBar.open('Success Deleted', 'Close', {
        duration: 3000, // milliseconds
        verticalPosition: 'top', // or 'bottom'
        panelClass: ['snackbar-success']
      });
      this.dialogRef.close({deleted: true, id: this.product.id});
    }, errors => {
      this.snackBar.open('Failed', 'Close', {
        duration: 3000, // milliseconds
        verticalPosition: 'top', // or 'bottom'
        panelClass: ['snackbar-success']
      });
      this.dialogRef.close({deleted: false});
    });
  }

  clearAddProductDetailsError(): void {
    this.errorBackend = false;
    this.productCodeError = '';
    this.preparationTimeError = '';
    this.errorMessageAr = '';
    this.errorMessageEn = '';
  }

}
