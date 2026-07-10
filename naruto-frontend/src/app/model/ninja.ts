import { Genre } from "./genre";
import { Utilisateur } from "./utilisateur";

export class Ninja extends Utilisateur {
  constructor (
      id: number,
      login: string,
      password: string,
      nom: string,
      prenom: string,
      genre: Genre,
      niveau: number,
      experience: number,
      pvMax: number,
      pvActuels: number,
      chakraMax: number,
      chakraActuel: number,
      nbEchecs: number,
      nbReussites: number,
      argent: number,
    public equipeId: number
  ) {
    super(id, login, password, nom, prenom, genre, niveau, experience, pvMax, pvActuels, chakraMax, chakraActuel, nbEchecs, nbReussites, argent)
  }
}
