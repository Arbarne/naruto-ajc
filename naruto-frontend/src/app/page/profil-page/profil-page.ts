import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, Subject, startWith, switchMap } from 'rxjs';
import { UtilisateurService } from '../../service/utilisateur-service';
import { AuthService } from '../../service/auth-service';
import { Profil } from '../../model/profil';
import { Navigation } from '../../component/navigation/navigation';
import { RangNinja } from '../../model/rang-ninja.enum';
import { Specialite } from '../../model/specialite.enum';
import { EtatNinja } from '../../model/etat-ninja.enum';

@Component({
  selector: 'app-profil-page',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    Navigation,
  ],
  templateUrl: './profil-page.html',
  styleUrl: './profil-page.css',
})
export class ProfilPage implements OnInit {

  private utilisateurService: UtilisateurService = inject(UtilisateurService);
  protected authService: AuthService = inject(AuthService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected readonly rangs = Object.values(RangNinja);
  protected readonly specialites = Object.values(Specialite);
  protected readonly etats = Object.values(EtatNinja);

  private refresh$: Subject<void> = new Subject<void>();
  protected profils$!: Observable<Profil[]>;

  protected selectedProfil: Profil | null = null;
  protected mode: '' | 'creer' | 'modifier' = '';

  protected loginCtrl!: FormControl;
  protected passwordCtrl!: FormControl;
  protected nomCtrl!: FormControl;
  protected prenomCtrl!: FormControl;
  protected genreCtrl!: FormControl;
  protected specialiteCtrl!: FormControl;
  protected rangCtrl!: FormControl;
  protected etatCtrl!: FormControl;
  protected niveauCtrl!: FormControl;
  protected expActuelCtrl!: FormControl;
  protected pvMaxCtrl!: FormControl;
  protected pvActuelCtrl!: FormControl;
  protected chakraMaxCtrl!: FormControl;
  protected chakraActuelCtrl!: FormControl;
  protected nbEchecsCtrl!: FormControl;
  protected nbReussitesCtrl!: FormControl;
  protected argentCtrl!: FormControl;

  protected ProfilForm!: FormGroup;

  ngOnInit(): void {
    this.loginCtrl = this.formBuilder.control('', Validators.required);
    this.passwordCtrl = this.formBuilder.control('');
    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.prenomCtrl = this.formBuilder.control('', Validators.required);
    this.genreCtrl = this.formBuilder.control('', Validators.required);
    this.specialiteCtrl = this.formBuilder.control(Specialite.AUCUNE, Validators.required);
    this.rangCtrl = this.formBuilder.control(RangNinja.GENIN, Validators.required);
    this.etatCtrl = this.formBuilder.control(EtatNinja.DISPONIBLE, Validators.required);
    this.niveauCtrl = this.formBuilder.control(1, Validators.required);
    this.expActuelCtrl = this.formBuilder.control(0, Validators.required);
    this.pvMaxCtrl = this.formBuilder.control(100, Validators.required);
    this.pvActuelCtrl = this.formBuilder.control(100, Validators.required);
    this.chakraMaxCtrl = this.formBuilder.control(100, Validators.required);
    this.chakraActuelCtrl = this.formBuilder.control(100, Validators.required);
    this.nbEchecsCtrl = this.formBuilder.control(0, Validators.required);
    this.nbReussitesCtrl = this.formBuilder.control(0, Validators.required);
    this.argentCtrl = this.formBuilder.control(0, Validators.required);

    this.ProfilForm = this.formBuilder.group({
      login: this.loginCtrl,
      password: this.passwordCtrl,
      nom: this.nomCtrl,
      prenom: this.prenomCtrl,
      genre: this.genreCtrl,
      specialite: this.specialiteCtrl,
      rang: this.rangCtrl,
      etat: this.etatCtrl,
      niveau: this.niveauCtrl,
      expActuel: this.expActuelCtrl,
      pvMax: this.pvMaxCtrl,
      pvActuel: this.pvActuelCtrl,
      chakraMax: this.chakraMaxCtrl,
      chakraActuel: this.chakraActuelCtrl,
      nbEchecs: this.nbEchecsCtrl,
      nbReussites: this.nbReussitesCtrl,
      argent: this.argentCtrl,
    });

    if (this.authService.role === 'ADMIN') {
      this.profils$ = this.refresh$.pipe(
        startWith(null),
        switchMap(() => this.utilisateurService.findAll())
      );
    }
  }

  private reload() {
    this.refresh$.next();
  }

  protected tauxReussite(profil: Profil): number {
    const total = profil.nbReussites + profil.nbEchecs;
    return total === 0 ? 0 : Math.round((profil.nbReussites / total) * 100);
  }

  protected pourcentage(actuel: number, max: number): number {
    return max === 0 ? 0 : Math.round((actuel / max) * 100);
  }

  public voirProfil(profil: Profil) {
    this.selectedProfil = profil;
    this.mode = '';
  }

  public fermerCarte() {
    this.selectedProfil = null;
    this.mode = '';
  }

  public nouveauProfil() {
    this.selectedProfil = null;
    this.mode = 'creer';
    this.ProfilForm.reset({
      specialite: Specialite.AUCUNE,
      rang: RangNinja.GENIN,
      etat: EtatNinja.DISPONIBLE,
      niveau: 1,
      expActuel: 0,
      pvMax: 100,
      pvActuel: 100,
      chakraMax: 100,
      chakraActuel: 100,
      nbEchecs: 0,
      nbReussites: 0,
      argent: 0,
    });
    this.passwordCtrl.setValidators(Validators.required);
    this.passwordCtrl.updateValueAndValidity();
  }

  public modifierProfil(profil: Profil) {
    this.mode = 'modifier';
    this.passwordCtrl.clearValidators();
    this.passwordCtrl.updateValueAndValidity();

    this.ProfilForm.setValue({
      login: profil.login,
      password: '',
      nom: profil.nom,
      prenom: profil.prenom,
      genre: profil.genre,
      specialite: profil.specialite,
      rang: profil.rang,
      etat: profil.etatNinja,
      niveau: profil.niveau,
      expActuel: profil.expActuel,
      pvMax: profil.pvMax,
      pvActuel: profil.pvActuel,
      chakraMax: profil.chakraMax,
      chakraActuel: profil.chakraActuel,
      nbEchecs: profil.nbEchecs,
      nbReussites: profil.nbReussites,
      argent: profil.argent,
    });
  }

  public annuler() {
    this.mode = '';
  }

  public creerOuModifier() {
    if (this.mode === 'creer') {
      this.utilisateurService.create(this.ProfilForm.getRawValue()).subscribe(() => {
        this.mode = '';
        this.reload();
      });
      return;
    }

    if (this.mode === 'modifier' && this.selectedProfil) {
      const request = { ...this.ProfilForm.getRawValue() };
      if (!request.password) {
        delete request.password;
      }

      this.utilisateurService.update(this.selectedProfil.id, request).subscribe(() => {
        this.mode = '';
        this.selectedProfil = null;
        this.reload();
      });
    }
  }

  public supprimer(profil: Profil) {
    this.utilisateurService.remove(profil.id).subscribe(() => {
      this.selectedProfil = null;
      this.mode = '';
      this.reload();
    });
  }
}
