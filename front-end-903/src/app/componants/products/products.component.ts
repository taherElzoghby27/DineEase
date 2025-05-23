import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../service/product.service';
import {Product} from '../../../model/product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private service: ProductService) {
  }

  ngOnInit(): void {
    this.getAllProducts();
  }

  getAllProducts(): void {
    this.service.getAllProducts().subscribe(products => {
      this.products = products;
    });
  }

}
