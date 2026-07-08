package naruto_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Ninja extends Utilisateur {

	@OneToMany
    @JoinColumn(name="equipe")
	@Column(name = "equipe", nullable = false)
	private Equipe equipe;

	public Ninja() {
	}

	public Ninja(Integer id, String login, String password, String prenom, String nom, Genre genre,
			Specialite specialite, RangNinja rang, EtatNinja etatNinja, int niveau, int expActuel, int pvMax,
			int pvActuel, int chakraMax, int chakraActuel, int nbEchecs, int nbReussites, int argent) {
		super(id, login, password, prenom, nom, genre, specialite, rang, etatNinja, niveau, expActuel, pvMax, pvActuel,
				chakraMax, chakraActuel, nbEchecs, nbReussites, argent);
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	@Override
	public String toString() {
		return "Ninja [equipe=" + equipe + "]";
	}

}
