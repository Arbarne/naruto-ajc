import { RangMission } from './rang-mission.enum';
import { EtatMission } from './etat-mission.enum';

export interface Mission {
  id: number;
  nom: string;
  description: string;
  rang: RangMission;
  gainExp: number;
  recompense: number;
  id_equipe: number;
  dateDebut: Date;
  dateFin: Date;
  statut: EtatMission;
}
