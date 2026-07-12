import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, Subject, catchError, of, startWith, switchMap } from 'rxjs';
import { EquipeService } from '../../service/equipe-service';
import { NinjaService } from '../../service/ninja-service';
import { LeaderService } from '../../service/leader-service';
import { AuthService } from '../../service/auth-service';
import { Equipe } from '../../model/equipe';
import { NinjaOption } from '../../model/ninja-option';
import { LeaderOption } from '../../model/leader-option';
import { Navigation } from '../../component/navigation/navigation';

@Component({
  selector: 'app-equipe-page',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    Navigation,
  ],
  templateUrl: './equipe-page.html',
  styleUrl: './equipe-page.css',
})
export class EquipePage implements OnInit {

  private equipeService: EquipeService = inject(EquipeService);
  private ninjaService: NinjaService = inject(NinjaService);
  private leaderService: LeaderService = inject(LeaderService);
  protected authService: AuthService = inject(AuthService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  private refresh$: Subject<void> = new Subject<void>();

  protected equipes$!: Observable<Equipe[]>;
  protected maEquipe$!: Observable<Equipe | null>;
  protected ninjaOptions$!: Observable<NinjaOption[]>;
  protected leaderOptions$!: Observable<LeaderOption[]>;

  protected showForm: boolean = false;
  protected editingEquipe: Equipe | null = null;
  protected EquipeForm!: FormGroup;
  protected nomCtrl!: FormControl;
  protected leaderIdCtrl!: FormControl;

  ngOnInit(): void {
    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.leaderIdCtrl = this.formBuilder.control('', Validators.required);

    this.EquipeForm = this.formBuilder.group({
      nom: this.nomCtrl,
      leaderId: this.leaderIdCtrl,
    });

    if (this.authService.role === 'ADMIN') {
      this.equipes$ = this.refresh$.pipe(
        startWith(null),
        switchMap(() => this.equipeService.findAll())
      );
      this.ninjaOptions$ = this.ninjaService.findAll();
      this.leaderOptions$ = this.leaderService.findAll();
      return;
    }

    this.maEquipe$ = this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.equipeService.findMine().pipe(
        catchError(() => of(null))
      ))
    );

    if (this.authService.role === 'LEADER') {
      this.ninjaOptions$ = this.ninjaService.findAll();
    }
  }

  private reload() {
    this.refresh$.next();
  }

  protected ninjasDisponibles(ninjas: NinjaOption[], equipe: Equipe): NinjaOption[] {
    return ninjas.filter(ninja => !equipe.ninjas.some(membre => membre.id === ninja.id));
  }

  public nouvelleEquipe() {
    this.showForm = true;
    this.editingEquipe = null;
    this.EquipeForm.reset();
  }

  public editer(equipe: Equipe) {
    this.showForm = true;
    this.editingEquipe = equipe;
    this.nomCtrl.setValue(equipe.nom);
    this.leaderIdCtrl.setValue(equipe.leaderId);
  }

  public annulerEditer() {
    this.showForm = false;
    this.editingEquipe = null;
    this.EquipeForm.reset();
  }

  public creerOuModifier() {
    const request = {
      nom: this.nomCtrl.value,
      leaderId: this.leaderIdCtrl.value,
    };

    const resultat = this.editingEquipe
      ? this.equipeService.update(this.editingEquipe.id, request)
      : this.equipeService.add(request);

    resultat.subscribe(() => {
      this.showForm = false;
      this.editingEquipe = null;
      this.EquipeForm.reset();
      this.reload();
    });
  }

  public creerMonEquipe() {
    this.equipeService.add({ nom: this.nomCtrl.value, leaderId: 0 }).subscribe(() => {
      this.EquipeForm.reset();
      this.reload();
    });
  }

  public supprimer(equipe: Equipe) {
    this.equipeService.remove(equipe).subscribe(() => this.reload());
  }

  public ajouterNinja(equipeId: number, ninjaId: number) {
    this.equipeService.addNinja(equipeId, ninjaId).subscribe(() => this.reload());
  }

  public retirerNinja(ninjaId: number) {
    this.equipeService.removeNinja(ninjaId).subscribe(() => this.reload());
  }
}
