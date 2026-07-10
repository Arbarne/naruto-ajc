import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-connexion-page',
  imports: [ReactiveFormsModule],
  templateUrl: './connexion-page.html',
  styleUrl: './connexion-page.css',
})
export class ConnexionPage {
  private formBuilder: FormBuilder = inject(FormBuilder);
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  private loginError = signal(false);
  protected usernameCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected passwordCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected connexionForm: FormGroup = this.formBuilder.group({
    login: this.usernameCtrl,
    password: this.passwordCtrl,
  });

  protected showPassword: boolean = false;
  protected usernameWalking: boolean = false;
  protected passwordWalking: boolean = false;

  public async connecter() {
    this.loginError.set(false);
    this.authService.auth(this.connexionForm.getRawValue()).subscribe({
      // next => si la réponse est OK
      next: resp => {
        if (resp.success == false) {
          this.loginError.set(true);
          return;
        }

        this.authService.token = resp.token;
        this.authService.role = resp.role;
        this.authService.equipeId = resp.equipeId;
        this.router.navigate([ '/home' ]);
      },

      // error => si la réponse est KO (30X, 40X, 50X)
      error: () => this.loginError.set(true)
    });
  }
}
