import { Genre } from "./genre";

export interface InscriptionRequest {
  login: string,
  password: string,
  nom: string,
  prenom: string,
  genre: Genre,

}
