import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  product = {
    name: '',
    description: '',
    price: null,
    image: '',
    preparationTime: null,
    productCode: ''
  };

  constructor() {
  }

  ngOnInit(): void {
  }


  isFormValid(): boolean {
    return (
      !!this.product.name?.trim() &&
      !!this.product.description?.trim() &&
      this.product.price !== null
    );
  }

  addProduct(): void {
    if (this.isFormValid()) {
      console.log('Product to be added:', this.product);
      // Send product to API or service here
    } else {
      console.warn('Required fields are missing');
    }
  }
}
