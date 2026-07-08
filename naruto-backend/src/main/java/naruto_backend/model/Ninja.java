package naruto_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Ninja")
public class Ninja extends Utilisateur {

	@OneToMany
    @JoinColumn(name="equipe")
	@Column(name = "equipe", nullable = false)
	private Equipe equipe;
}
