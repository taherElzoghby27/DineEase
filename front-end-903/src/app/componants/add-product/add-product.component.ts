import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../service/product.service';
import {ProductRequest} from '../../../model/product-request';
import {Category} from '../../../model/category';
import {CategoryService} from '../../../service/category.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  name = '';
  description = '';
  price = 0.0;
  image = '';
  categoryId?: number;
  categories: Category[] = [];
  errorAr = '';
  errorEn = '';
  errorBackend = false;

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => this.getAllCategories());
  }


  isFormValid(): boolean {
    return !(this.name === '' || this.description === '' || this.price <= 0 || (this.categoryId === undefined));
  }

  addProduct(): void {
    if (!this.isFormValid()) {
      return;
    }
    const productRequest = new ProductRequest({
      name: this.name,
      imagePath: this.image,
      description: this.description,
      price: this.price,
      categoryId: this.categoryId,
    });

    this.productService.createProduct(productRequest).subscribe(
      product => {
        this.errorBackend = false;
        this.snackBar.open('Product Added Successfully', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.router.navigateByUrl('products');
      },
      errors => {
        this.errorBackend = true;
        this.errorAr = errors.error.bundleMessage.message_ar;
        this.errorEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  getAllCategories(): void {
    this.categoryService.getAllCategories().subscribe(
      response => {
        this.categories = response;
      },
      errors => {
      }
    );
  }
}
