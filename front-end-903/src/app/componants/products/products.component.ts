import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../service/product.service';
import {Product} from '../../../model/product';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private service: ProductService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => this.handleAllActions());
  }

  handleAllActions(): void {
    const hasId = this.activatedRoute.snapshot.paramMap.has('id');
    const hasKey = this.activatedRoute.snapshot.paramMap.has('key');
    if (hasId) {
      const categoryId = this.activatedRoute.snapshot.paramMap.get('id');
      this.getAllProductsByCategoryId(categoryId);
    } else if (hasKey) {
      const key = this.activatedRoute.snapshot.paramMap.get('key');
      this.getProductsByKey(key);
    } else {
      this.getAllProducts();
    }
  }

  getAllProducts(): void {
    this.service.getAllProducts().subscribe(products => {
      this.products = products;
    });
  }

  getAllProductsByCategoryId(id: string): void {
    this.service.getProductsByCategoryId(id).subscribe(products => {
      this.products = products;
    });
  }

  getProductsByKey(key: string): void {
    this.service.getProductsByKey(key).subscribe(products => {
      this.products = products;
    });
  }

}
