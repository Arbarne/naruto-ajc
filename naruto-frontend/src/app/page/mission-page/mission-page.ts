import { AuthService } from './../../service/auth-service';
import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { MissionService } from '../../service/mission-service';
import { EquipeService } from '../../service/equipe-service';
import { Mission } from '../../model/mission';
import { Equipe } from '../../model/equipe';
import { RangMission } from '../../model/rang-mission.enum';
import { EtatMission } from '../../model/etat-mission.enum';
import { MissionListView } from '../../model/mission-list-view';
import { Navigation } from '../../component/navigation/navigation';

@Component({
  selector: 'app-mission-page',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    Navigation,
  ],
  templateUrl: './mission-page.html',
  styleUrl: './mission-page.css',
})
export class MissionPage implements OnInit {

  private missionService: MissionService = inject(MissionService);
  private equipeService: EquipeService = inject(EquipeService);
  protected authService: AuthService = inject(AuthService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected readonly rangs = Object.values(RangMission);
  protected readonly etats = Object.values(EtatMission);

  protected mission$!: Observable<MissionListView[]>;
  protected currentMission$!: Observable<MissionListView>;
  private refresh$: Subject<void> = new Subject<void>();
  protected equipe$!: Observable<Equipe[]>;

  protected show: "" | "detail" | "modify" | "assign" = ""

  protected MissionForm!: FormGroup;
  protected nomCtrl!: FormControl;
  protected descriptionCtrl!: FormControl;
  protected rangCtrl!: FormControl;
  protected gainExpCtrl!: FormControl;
  protected recompenseCtrl!: FormControl;
  protected equipeCtrl!: FormControl;
  protected dateDebutCtrl!: FormControl;
  protected dateFinCtrl!: FormControl;
  protected statutCtrl!: FormControl;

  protected AssignForm!: FormGroup;

  protected editingMission!: Mission | null;

  ngOnInit(): void {
    this.mission$ = this.refresh$.pipe(
      startWith(null),

      switchMap(() => {
        return this.missionService.findAll();
      })
    );
    this.currentMission$ = this.refresh$.pipe(
      startWith(null),

      switchMap(() => {
        return this.missionService.findMine();
      })
    );

    this.equipe$ = this.equipeService.findAll();



    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.descriptionCtrl = this.formBuilder.control('', Validators.required);
    this.rangCtrl = this.formBuilder.control('', Validators.required);
    this.gainExpCtrl = this.formBuilder.control('', Validators.required);
    this.recompenseCtrl = this.formBuilder.control('', Validators.required);
    this.equipeCtrl = this.formBuilder.control('');
    this.dateDebutCtrl = this.formBuilder.control('');
    this.dateFinCtrl = this.formBuilder.control('');
    this.statutCtrl = this.formBuilder.control('');

    this.MissionForm = this.formBuilder.group({
      nom: this.nomCtrl,
      description: this.descriptionCtrl,
      rang: this.rangCtrl,
      gainExp: this.gainExpCtrl,
      recompense: this.recompenseCtrl,
      equipeId: this.equipeCtrl,
      dateDebut: this.dateDebutCtrl,
      dateFin: this.dateFinCtrl,
      statut: this.statutCtrl
    });

    this.AssignForm = this.formBuilder.group({
      equipeId: this.equipeCtrl
    })
  }

  private reload() {
    this.refresh$.next();
  }

  public nouvelleMission() {
    this.show = "modify";
    this.editingMission = null;
    this.MissionForm.reset();
  }

  public creerOuModifier() {
    const mission: Mission = {
      ...this.MissionForm.getRawValue(),
      id: this.editingMission?.id
    };

    this.missionService.save(mission).subscribe(() => {
      this.show = "";
      this.editingMission = null;
      this.MissionForm.reset();

      this.reload();
    });
  }

  public editer(id: number, show: "modify" | "detail" | "assign") {
    this.show = show;
    console.log(show)

    this.missionService.findById(id).subscribe((mission) => {
      this.editingMission = mission;

      this.nomCtrl.setValue(mission.nom);
      this.descriptionCtrl.setValue(mission.description);
      this.rangCtrl.setValue(mission.rang);
      this.gainExpCtrl.setValue(mission.gainExp);
      this.recompenseCtrl.setValue(mission.recompense);
      this.equipeCtrl.setValue(mission.equipeId);
      this.dateDebutCtrl.setValue(mission.dateDebut);
      this.dateFinCtrl.setValue(mission.dateFin);
      this.statutCtrl.setValue(mission.statut);
    })
  }

  public annulerEditer() {
    this.show = "";
    this.editingMission = null;
    this.MissionForm.reset();
  }

  public supprimer(id: number) {
    this.missionService.deleteById(id).subscribe(() => this.reload());
  }

  public ouvrirAssignMenu(id: number) {
    this.show = "assign"

  }

  public assigner(id: number) {
    this.editer(id, "assign")
  }

  public demarrer(id: number) {
    const observable = this.missionService.demarrer(id)
    if (observable) {
      observable.subscribe(() => this.reload())
    }
  }

  public terminer(id: number) {
    const observable = this.missionService.terminer(id)
    if (observable) {
      observable.subscribe(() => this.reload())
    }
  }

  public voir(id: number) {
    this.editer(id, "detail")
  }

  getEquipeLabel(equipeId: number | string, equipes: Equipe[]): string {
    const equipe = equipes.find(e => e.id === equipeId);
    return equipe ? equipe.nom : '';
  }
}
