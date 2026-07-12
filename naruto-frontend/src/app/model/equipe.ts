export interface NinjaSimple {
  id: number;
  nom: string;
}

export interface Equipe {
  id: number;
  nom: string;
  leaderId: number;
  leaderNom: string;
  ninjas: NinjaSimple[];
}
