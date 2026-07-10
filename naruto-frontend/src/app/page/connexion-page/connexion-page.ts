import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-connexion-page',
  imports: [ReactiveFormsModule],
  templateUrl: './connexion-page.html',
  styleUrl: './connexion-page.css',
})
export class ConnexionPage {
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected usernameCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected passwordCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected connexionForm: FormGroup = this.formBuilder.group({
    username: this.usernameCtrl,
    password: this.passwordCtrl,
  });

  protected showPassword: boolean = false;
  protected usernameWalking: boolean = false;
  protected passwordWalking: boolean = false;
}
