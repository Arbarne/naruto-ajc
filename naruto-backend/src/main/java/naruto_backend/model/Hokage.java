package naruto_backend.model;

import jakarta.persistence.Entity;

@Entity
public class Hokage extends Utilisateur {

	public Hokage() {
	}

	public Hokage(Integer id, String login, String password, String prenom, String nom, Genre genre,
			Specialite specialite, RangNinja rang, EtatNinja etat, int niveau, int expActuel, int pvMax,
			int pvActuel, int chakraMax, int chakraActuel, int nbEchecs, int nbReussites, int argent) {
		super(id, login, password, prenom, nom, genre, specialite, rang, etat, niveau, expActuel, pvMax, pvActuel,
				chakraMax, chakraActuel, nbEchecs, nbReussites, argent);
	}
	
}
