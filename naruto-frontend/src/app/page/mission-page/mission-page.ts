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

@Component({
  selector: 'app-mission-page',
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './mission-page.html',
  styleUrl: './mission-page.css',
})
export class MissionPage implements OnInit {
  private missionService: MissionService = inject(MissionService);
  private equipeService: EquipeService = inject(EquipeService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected readonly rangs = Object.values(RangMission);
  protected readonly etats = Object.values(EtatMission);

  protected mission$!: Observable<Mission[]>;
  private refresh$: Subject<void> = new Subject<void>();
  protected equipe$!: Observable<Equipe[]>;

  protected showForm: boolean = false;
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
  protected editingMission!: Mission | null;

  ngOnInit(): void {
    this.mission$ = this.refresh$.pipe(
      startWith(null),

      switchMap(() => {
        return this.missionService.findAll();
      })
    );

    this.equipe$ = this.equipeService.findAll();

    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.descriptionCtrl = this.formBuilder.control('', Validators.required);
    this.rangCtrl = this.formBuilder.control('', Validators.required);
    this.gainExpCtrl = this.formBuilder.control('', Validators.required);
    this.recompenseCtrl = this.formBuilder.control('', Validators.required);
    this.equipeCtrl = this.formBuilder.control('', Validators.required);
    this.dateDebutCtrl = this.formBuilder.control('', Validators.required);
    this.dateFinCtrl = this.formBuilder.control('', Validators.required);
    this.statutCtrl = this.formBuilder.control('', Validators.required);

    this.MissionForm = this.formBuilder.group({
      nom: this.nomCtrl,
      description: this.descriptionCtrl,
      rang: this.rangCtrl,
      gainExp: this.gainExpCtrl,
      recompense: this.recompenseCtrl,
      id_equipe: this.equipeCtrl,
      dateDebut: this.dateDebutCtrl,
      dateFin: this.dateFinCtrl,
      statut: this.statutCtrl
    });
  }

  private reload() {
    this.refresh$.next();
  }

  public creerOuModifier() {
    const mission: Mission = {
      ...this.MissionForm.getRawValue(),
      id: this.editingMission?.id
    };

    this.missionService.save(mission).subscribe(() => {
      this.showForm = false;
      this.editingMission = null;
      this.MissionForm.reset();

      this.reload();
    });
  }

  public editer(mission: Mission) {
    this.editingMission = mission;
    this.showForm = true;

    this.nomCtrl.setValue(mission.nom);
    this.descriptionCtrl.setValue(mission.description);
    this.rangCtrl.setValue(mission.rang);
    this.gain_expCtrl.setValue(mission.gain_exp);
    this.recompenseCtrl.setValue(mission.recompense);
    this.equipeCtrl.setValue(mission.id_equipe);
    this.dateDebutCtrl.setValue(mission.dateDebut);
    this.dateFinCtrl.setValue(mission.dateFin);
    this.statutCtrl.setValue(mission.statut);
  }

  public annulerEditer() {
    this.showForm = false;
    this.editingMission = null;
    this.MissionForm.reset();
  }

  public supprimer(mission: Mission) {
    this.missionService.deleteById(mission.id).subscribe(() => this.reload());
  }
}
