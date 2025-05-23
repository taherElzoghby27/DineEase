import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../../../service/category.service';
import {Category} from '../../../model/category';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  constructor(private service: CategoryService) {
  }

  categories: Category[] = [];

  ngOnInit(): void {
    this.getAllCategories();
  }

  getAllCategories(): void {
    this.service.getAllCategories().subscribe(
      categories => this.categories = categories
    );
  }
}
