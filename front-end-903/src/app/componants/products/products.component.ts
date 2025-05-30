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
  page = 1;
  size = 10;
  collectionSize = 0;
  maxSize = 3;

  constructor(private service: ProductService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => this.handleAllActions(this.page));
  }

  handleAllActions(pageNumber): void {
    const hasId = this.activatedRoute.snapshot.paramMap.has('id');
    const hasKey = this.activatedRoute.snapshot.paramMap.has('key');
    if (hasId && hasKey) {
      const categoryId = this.activatedRoute.snapshot.paramMap.get('id');
      const key = this.activatedRoute.snapshot.paramMap.get('key');
      this.getProductsByKeyAndCategoryId(key, categoryId, pageNumber);
    } else if (hasId) {
      const categoryId = this.activatedRoute.snapshot.paramMap.get('id');
      this.getAllProductsByCategoryId(categoryId, pageNumber);
    } else if (hasKey) {
      const key = this.activatedRoute.snapshot.paramMap.get('key');
      this.getProductsByKey(key, pageNumber);
    } else {
      this.getAllProducts(pageNumber);
    }
  }

  getAllProducts(pageNumber): void {
    this.service.getAllProducts(pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    });
  }

  getAllProductsByCategoryId(id: string, pageNumber): void {
    this.service.getProductsByCategoryId(id, pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    });
  }

  getProductsByKey(key: string, pageNumber): void {
    this.service.getProductsByKey(key, pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    });
  }

  getProductsByKeyAndCategoryId(key: string, id: string, pageNumber): void {
    this.service.searchByCategoryIdAndKey(id, key, pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    });
  }

  doPagination(): void {
    this.handleAllActions(this.page);
  }

  changeSizeOfItems(event: Event): void {
    this.size = +(event.target as HTMLInputElement).value;
    this.handleAllActions(this.page);
  }

}
