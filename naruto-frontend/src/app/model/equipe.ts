import { NinjaOption } from './ninja-option';

export interface Equipe {
  id: number;
  nom: string;
  leaderId: number;
  leaderNom: string;
  ninjas: NinjaOption[];
}
