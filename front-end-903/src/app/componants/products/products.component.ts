import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../service/product.service';
import {Product} from '../../../model/product';
import {ActivatedRoute} from '@angular/router';
import {CardOrder} from '../../../model/card-order';
import {CardService} from '../../../service/card.service';

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
  messageAr = '';
  messageEn = '';
  isError = false;

  constructor(private service: ProductService, private activatedRoute: ActivatedRoute, private cardService: CardService) {
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
    }, errors => {
      this.products = [];
      this.collectionSize = 0;
      this.messageAr = errors.error.bundleMessage.message_ar;
      this.messageEn = errors.error.bundleMessage.message_en;
      this.isError = true;
    });
  }

  getAllProductsByCategoryId(id: string, pageNumber): void {
    this.service.getProductsByCategoryId(id, pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    }, errors => {
      this.products = [];
      this.collectionSize = 0;
      this.messageAr = errors.error.bundleMessage.message_ar;
      this.messageEn = errors.error.bundleMessage.message_en;
      this.isError = true;
    });
  }

  getProductsByKey(key: string, pageNumber): void {
    this.service.getProductsByKey(key, pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    }, errors => {
      this.products = [];
      this.collectionSize = 0;
      this.messageAr = errors.error.bundleMessage.message_ar;
      this.messageEn = errors.error.bundleMessage.message_en;
      this.isError = true;
    });
  }

  getProductsByKeyAndCategoryId(key: string, id: string, pageNumber): void {
    this.service.searchByCategoryIdAndKey(id, key, pageNumber, this.size).subscribe(response => {
      this.products = response.products;
      this.collectionSize = response.totalProducts;
    }, errors => {
      this.products = [];
      this.collectionSize = 0;
      this.messageAr = errors.error.bundleMessage.message_ar;
      this.messageEn = errors.error.bundleMessage.message_en;
      this.isError = true;
    });
  }

  doPagination(): void {
    this.handleAllActions(this.page);
  }

  changeSizeOfItems(event: Event): void {
    this.size = +(event.target as HTMLInputElement).value;
    this.handleAllActions(this.page);
  }

  addToCard(product: Product): void {
    const order: CardOrder = new CardOrder(product);
    this.cardService.addProductOrder(order);
  }

}
