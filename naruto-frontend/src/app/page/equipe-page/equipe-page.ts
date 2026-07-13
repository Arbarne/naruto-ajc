import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, Subject, catchError, forkJoin, of, startWith, switchMap } from 'rxjs';
import { EquipeService, EquipeRequest } from '../../service/equipe-service';
import { NinjaService } from '../../service/ninja-service';
import { LeaderService } from '../../service/leader-service';
import { MissionService } from '../../service/mission-service';
import { AuthService } from '../../service/auth-service';
import { Equipe } from '../../model/equipe';
import { NinjaOption } from '../../model/ninja-option';
import { LeaderOption } from '../../model/leader-option';
import { MissionListView } from '../../model/mission-list-view';
import { Navigation } from '../../component/navigation/navigation';

const STATUTS_MISSION_EN_COURS = ['Disponible', 'EnCours'];

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
  private missionService: MissionService = inject(MissionService);
  protected authService: AuthService = inject(AuthService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  private refresh$: Subject<void> = new Subject<void>();

  protected equipes$!: Observable<Equipe[]>;
  protected maEquipe$!: Observable<Equipe | null>;
  protected ninjaOptions$!: Observable<NinjaOption[]>;
  protected leaderOptions$!: Observable<LeaderOption[]>;
  protected missions$!: Observable<MissionListView[]>;

  // Formulaire creer/modifier une equipe (Hokage)
  protected showForm: boolean = false;
  protected editingEquipe: Equipe | null = null;
  protected EquipeForm!: FormGroup;
  protected nomCtrl!: FormControl;
  protected leaderIdCtrl!: FormControl;

  // Gestion des ninjas (checklist), commune Hokage (dans le formulaire) et Leader (panneau dedie)
  protected ninjasSelectionnes: Set<number> = new Set();
  private ninjasOriginaux: Set<number> = new Set();
  protected gestionNinjasOuverte: boolean = false;

  ngOnInit(): void {
    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.leaderIdCtrl = this.formBuilder.control('', Validators.required);

    this.EquipeForm = this.formBuilder.group({
      nom: this.nomCtrl,
      leaderId: this.leaderIdCtrl,
    });

    // Visible par tout le monde : sert a afficher la mission assignee de chaque equipe
    this.missions$ = this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.missionService.findAll())
    );

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

  // Mission actuellement en cours/disponible pour cette equipe (par nom, unique en base), s'il y en a une
  protected missionAssignee(equipeNom: string, missions: MissionListView[]): string | null {
    const mission = missions.find(m =>
      m.equipeNom === equipeNom && STATUTS_MISSION_EN_COURS.includes(m.statut)
    );
    return mission ? mission.nom : null;
  }

  // Pour un simple Ninja (USER) : n'affiche que ses coequipiers, pas lui-meme
  protected coequipiers(equipe: Equipe): NinjaOption[] {
    if (this.authService.role !== 'USER') {
      return equipe.ninjas;
    }
    return equipe.ninjas.filter(ninja => ninja.login !== this.authService.login);
  }

  public toggleNinjaSelection(ninjaId: number, selectionne: boolean) {
    if (selectionne) {
      this.ninjasSelectionnes.add(ninjaId);
    } else {
      this.ninjasSelectionnes.delete(ninjaId);
    }
  }

  // Calcule les ajouts/retraits entre la selection d'origine et la nouvelle, puis les applique
  private appliquerChangementsNinjas(equipeId: number): Observable<unknown> {
    const aAjouter = Array.from(this.ninjasSelectionnes).filter(id => !this.ninjasOriginaux.has(id));
    const aRetirer = Array.from(this.ninjasOriginaux).filter(id => !this.ninjasSelectionnes.has(id));

    const appels = [
      ...aAjouter.map(id => this.equipeService.addNinja(equipeId, id)),
      ...aRetirer.map(id => this.equipeService.removeNinja(id)),
    ];

    return appels.length > 0 ? forkJoin(appels) : of(null);
  }

  // --- Hokage : creer/modifier une equipe ---

  public nouvelleEquipe() {
    this.showForm = true;
    this.editingEquipe = null;
    this.ninjasSelectionnes = new Set();
    this.ninjasOriginaux = new Set();
    this.EquipeForm.reset();
  }

  public editer(equipe: Equipe) {
    this.showForm = true;
    this.editingEquipe = equipe;
    this.nomCtrl.setValue(equipe.nom);
    this.leaderIdCtrl.setValue(equipe.leaderId);
    this.ninjasOriginaux = new Set(equipe.ninjas.map(ninja => ninja.id));
    this.ninjasSelectionnes = new Set(this.ninjasOriginaux);
  }

  public annulerEditer() {
    this.showForm = false;
    this.editingEquipe = null;
    this.ninjasSelectionnes = new Set();
    this.ninjasOriginaux = new Set();
    this.EquipeForm.reset();
  }

  public creerOuModifier() {
    const request: EquipeRequest = {
      nom: this.nomCtrl.value,
      leaderId: this.leaderIdCtrl.value,
    };

    if (!this.editingEquipe) {
      request.ninjasId = Array.from(this.ninjasSelectionnes);

      this.equipeService.add(request).subscribe(() => {
        this.showForm = false;
        this.ninjasSelectionnes = new Set();
        this.EquipeForm.reset();
        this.reload();
      });
      return;
    }

    const equipeId = this.editingEquipe.id;

    this.equipeService.update(equipeId, request).pipe(
      switchMap(() => this.appliquerChangementsNinjas(equipeId))
    ).subscribe(() => {
      this.showForm = false;
      this.editingEquipe = null;
      this.ninjasSelectionnes = new Set();
      this.ninjasOriginaux = new Set();
      this.EquipeForm.reset();
      this.reload();
    });
  }

  public supprimer(equipe: Equipe) {
    this.equipeService.remove(equipe).subscribe(() => this.reload());
  }

  // --- Leader : creer sa propre equipe, gerer ses ninjas ---

  public creerMonEquipe() {
    this.equipeService.add({ nom: this.nomCtrl.value, leaderId: 0 }).subscribe(() => {
      this.EquipeForm.reset();
      this.reload();
    });
  }

  public ouvrirGestionNinjas(equipe: Equipe) {
    this.ninjasOriginaux = new Set(equipe.ninjas.map(ninja => ninja.id));
    this.ninjasSelectionnes = new Set(this.ninjasOriginaux);
    this.gestionNinjasOuverte = true;
  }

  public annulerGestionNinjas() {
    this.gestionNinjasOuverte = false;
    this.ninjasSelectionnes = new Set();
    this.ninjasOriginaux = new Set();
  }

  public enregistrerNinjas(equipe: Equipe) {
    this.appliquerChangementsNinjas(equipe.id).subscribe(() => {
      this.gestionNinjasOuverte = false;
      this.ninjasSelectionnes = new Set();
      this.ninjasOriginaux = new Set();
      this.reload();
    });
  }
}
