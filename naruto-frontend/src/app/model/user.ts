import { Genre } from "./genre";

export class User {
  constructor (
    public id: number,
    public login: string,
    public password: string,
    public nom: string,
    public prenom: string,
    public genre: Genre,
    public niveau: number,
    public experience: number,
    public pvMax: number,
    public pvActuels: number,
    public chakraMax: number,
    public chakraActuel: number,
    public nbEchecs: number,
    public nbReussites: number,
    public argent: number,
  ) {}


}
