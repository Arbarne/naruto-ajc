import { EtatMission } from "./etat-mission.enum";
import { RangMission } from "./rang-mission.enum";

export interface MissionListView {
  id: number,
  nom: string,
  description: string,
  rang: RangMission,
  equipeNom: string,
  statut: EtatMission,
  dateDebut: Date,
  dateFin: Date
}
