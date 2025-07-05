import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../../../service/category.service';
import {Category} from '../../../model/category';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {
  selectedLogo = '';
  name = '';
  flag = '';
  errorSelectedLogo = '';
  errorName = '';
  errorFlag = '';
  errorAr = '';
  errorEn = '';
  errorBackend = false;

  flags: string[] = [
    'Popular', 'Lovely', 'Special', 'New', 'Top Pick',
    'Must Try', 'Featured', 'Trending', 'Best Seller',
    'Hot Item', 'Chef’s Choice', 'Business Favorite', 'Combo Deal'
  ];

  icons = [
    {class: 'fas fa-pizza-slice', label: 'Pizza Slice'},
    {class: 'fas fa-hamburger', label: 'Hamburger'},
    {class: 'fas fa-ice-cream', label: 'Ice Cream'},
    {class: 'fas fa-coffee', label: 'Coffee'},
    {class: 'fas fa-fish', label: 'Fish'},
    {class: 'fas fa-utensils', label: 'Utensils'},
    {class: 'fas fa-bacon', label: 'Bacon'},
    {class: 'fas fa-apple-alt', label: 'Apple'},
    // Add more icons as needed
  ];

  constructor(private categoryService: CategoryService, private snackBar: MatSnackBar, private router: Router) {
  }

  ngOnInit(): void {
  }

  onLogoChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    this.selectedLogo = select.value;
  }

  addCategory(): void {
    if (!this.validation()) {
      return;
    }
    const category = new Category(this.name, this.selectedLogo, this.flag, null);
    this.categoryService.createCategory(category).subscribe(
      response => {
        this.clearError();
        this.snackBar.open('Category Added Successfully', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.router.navigateByUrl('products');
      },
      errors => {
        this.clearError();
        this.errorBackend = true;
        this.errorAr = errors.error.bundleMessage.message_ar;
        this.errorEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  validation(): boolean {
    this.clearError();
    if (this.name === '') {
      this.errorName = 'Name Required';
      return false;
    }
    if (this.selectedLogo === '') {
      this.errorSelectedLogo = 'Logo Required';
      return false;
    }
    if (this.flag === '') {
      this.errorFlag = 'Flag Required';
      return false;
    }
    return true;
  }

  clearError(): void {
    this.errorName = '';
    this.errorFlag = '';
    this.errorSelectedLogo = '';
    this.errorBackend = false;
  }
}
