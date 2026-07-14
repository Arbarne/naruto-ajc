import { Genre } from './genre';
import { RangNinja } from './rang-ninja.enum';
import { Specialite } from './specialite.enum';
import { EtatNinja } from './etat-ninja.enum';

export interface ProfilEquipe {
  id: number;
  nom: string | null;
}

export type UserType = 'Ninja' | 'Leader' | 'Hokage';

export interface Profil {
  id: number;
  type: UserType;
  login: string;
  nom: string;
  prenom: string;
  genre: Genre;
  specialite: Specialite;
  rang: RangNinja;
  etatNinja: EtatNinja;
  niveau: number;
  expActuel: number;
  pvMax: number;
  pvActuel: number;
  chakraMax: number;
  chakraActuel: number;
  nbEchecs: number;
  nbReussites: number;
  argent: number;
  equipeBasicInfo: ProfilEquipe;
}

export interface ProfilUpdateRequest {
  login: string;
  password?: string;
  nom: string;
  prenom: string;
  genre: Genre;
  specialite: Specialite;
  rang: RangNinja;
  etat: EtatNinja;
  niveau: number;
  expActuel: number;
  pvMax: number;
  pvActuel: number;
  chakraMax: number;
  chakraActuel: number;
  nbEchecs: number;
  nbReussites: number;
  argent: number;
}
