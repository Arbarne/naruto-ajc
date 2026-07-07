package naruto_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Leader extends Utilisateur {
	@OneToOne(mappedBy = "leader")
    @JoinColumn
	@Column(name = "equipe", nullable = false)
	private Equipe equipe;
}
