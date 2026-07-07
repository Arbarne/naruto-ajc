package naruto_backend.model;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import naruto_backend.view.Views;

@Entity
@Table(name="utilisateur")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;

	@Column(length = 50,nullable = false)
	@JsonView(Views.Common.class)
	private String login;  

	@Column(length = 255,nullable = false)
	@JsonView(Views.Common.class)
	private String password;

	@Column(length = 35,nullable = false)
	@JsonView(Views.Common.class)
	private String prenom; 

	@Column(length = 35,nullable = true)
	@JsonView(Views.Common.class)
	private String nom;

	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private Genre genre;
	
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private Specialite specialite; 

	@Column(nullable = true)
	@JsonView(Views.Common.class)
	private RangNinja rang; 
	
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int niveau;
	
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int expActuel;

	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int pvMax;

	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int pvActuel;
	
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int chakraMax;
	
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int chakraActuel;
	
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int nbEchecs;

	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int nbReussites;

	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private int argent;
}
