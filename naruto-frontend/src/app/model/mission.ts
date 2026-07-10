import { RangMission } from './rang-mission.enum';
import { EtatMission } from './etat-mission.enum';

export interface Mission {
  id: number;
  nom: string;
  description: string;
  rang: RangMission;
  gainExp: number;
  recompense: number;
  equipeId: number;
  dateDebut: Date;
  dateFin: Date;
  statut: EtatMission;
}
