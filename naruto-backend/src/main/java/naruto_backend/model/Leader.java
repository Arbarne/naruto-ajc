package naruto_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Leader")
public class Leader extends Utilisateur {
	@OneToOne(mappedBy = "leader")
	@Column(name = "equipe", nullable = false)
	private Equipe equipe;

	public Leader(Integer id, String login, String password, String prenom, String nom, Genre genre,
			Specialite specialite, RangNinja rang, EtatNinja etatNinja, int niveau, int expActuel, int pvMax,
			int pvActuel, int chakraMax, int chakraActuel, int nbEchecs, int nbReussites, int argent, Equipe equipe) {
		super(id, login, password, prenom, nom, genre, specialite, rang, etatNinja, niveau, expActuel, pvMax, pvActuel,
				chakraMax, chakraActuel, nbEchecs, nbReussites, argent);
		this.equipe = equipe;
	}
	
	public Leader() {
		
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
}
