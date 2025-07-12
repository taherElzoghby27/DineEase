import {Component} from '@angular/core';
import {AuthService} from '../../../service/auth.service';
import {RequestChangePassword} from '../../../model/request-change-password';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  oldPassword = '';
  newPassword = '';
  errorMessage = '';
  errorMessageAr = '';
  errorMessageEn = '';
  errorFromBackend = false;

  constructor(private authService: AuthService, private snackBar: MatSnackBar, private router: Router) {
  }


  onChangePassword(): void {
    if (!this.validate()) {
      return;
    }
    const username = sessionStorage.getItem('userName');
    const requestChangePassword = new RequestChangePassword(
      username,
      this.oldPassword,
      this.newPassword
    );
    this.authService.changePassword(requestChangePassword).subscribe(
      response => {
        this.errorMessage = '';
        this.snackBar.open('Success Changed', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.router.navigateByUrl('products');
      },
      errors => {
        this.clearErrors();
        this.errorFromBackend = true;
        this.errorMessageAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      }
    );
  }

  validate(): boolean {
    this.clearErrors();
    if (!this.oldPassword || !this.newPassword) {
      this.errorMessage = 'Please fill in both fields.';
      return false;
    }

    // Example logic (replace with actual API call)
    if (this.oldPassword === this.newPassword) {
      this.errorMessage = 'New password must be different from the old one.';
      return false;
    }
    return true;
  }

  clearErrors(): void {
    this.errorMessage = '';
    this.errorMessageEn = '';
    this.errorFromBackend = false;
    this.errorMessageAr = '';
  }

}
