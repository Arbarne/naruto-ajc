import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { EquipeService } from '../../service/equipe-service';
import { Equipe } from '../../model/equipe';

@Component({
  selector: 'app-equipe-page',
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './equipe-page.html',
  styleUrl: './equipe-page.css',
})
export class EquipePage implements OnInit {
  private equipeService: EquipeService = inject(EquipeService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected equipe$!: Observable<Equipe[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected showForm: boolean = false;
  protected EquipeForm!: FormGroup;
  protected leaderCtrl!: FormControl;
  protected editingEquipe!: Equipe | null;

  ngOnInit(): void {
    this.equipe$ = this.refresh$.pipe(
      startWith(null),

      switchMap(() => {
        return this.equipeService.findAll();
      })
    );

    this.leaderCtrl = this.formBuilder.control('', Validators.required);

    this.EquipeForm = this.formBuilder.group({
      id_leader: this.leaderCtrl
    });
  }

  private reload() {
    this.refresh$.next();
  }

  public creerOuModifier() {
    const equipe: Equipe = {
      ...this.EquipeForm.getRawValue(),
      id: this.editingEquipe?.id
    };

    this.equipeService.save(equipe).subscribe(() => {
      this.showForm = false;
      this.editingEquipe = null;
      this.EquipeForm.reset();

      this.reload();
    });
  }

  public editer(equipe: Equipe) {
    this.editingEquipe = equipe;
    this.showForm = true;

    this.leaderCtrl.setValue(equipe.id_leader);
  }

  public annulerEditer() {
    this.showForm = false;
    this.editingEquipe = null;
    this.EquipeForm.reset();
  }

  public supprimer(equipe: Equipe) {
    this.equipeService.remove(equipe).subscribe(() => this.reload());
  }
}
