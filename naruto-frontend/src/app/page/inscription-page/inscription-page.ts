import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UtilisateurService } from '../../service/utilisateur-service';

@Component({
  selector: 'app-inscription-page',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './inscription-page.html',
  styleUrl: './inscription-page.css',
})
export class InscriptionPage {
  private formBuilder: FormBuilder = inject(FormBuilder);
  private utilisateurService: UtilisateurService = inject(UtilisateurService);
  private router: Router = inject(Router);

  protected inscriptionError = signal(false);

  protected loginCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected passwordCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected nomCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected prenomCtrl: FormControl = this.formBuilder.control('', Validators.required);
  protected genreCtrl: FormControl = this.formBuilder.control('', Validators.required);

  protected inscriptionForm: FormGroup = this.formBuilder.group({
    login: this.loginCtrl,
    password: this.passwordCtrl,
    nom: this.nomCtrl,
    prenom: this.prenomCtrl,
    genre: this.genreCtrl,
  });

  protected showPassword: boolean = false;

  public inscrire() {
    this.inscriptionError.set(false);

    this.utilisateurService.inscrire(this.inscriptionForm.getRawValue()).subscribe({
      next: () => this.router.navigate(['/connexion']),
      error: () => this.inscriptionError.set(true),
    });
  }
}
