package naruto_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50,nullable = false)
	private String login;  

	@Column(length = 255,nullable = false)

	private String password;
	@Column(length = 35,nullable = false)

	private String prenom; 
	@Column(length = 35,nullable = true)

	private String nom;
	@Column(nullable = false)

	private Genre genre;
	@Column(nullable = false)

	private Specialite specialite; 
	@Column(nullable = true)

	private RangNinja rang; 
	@Column(nullable = true)

	private EtatNinja etatNinja; 
	@Column(nullable = false)
	private int niveau;
	
	@Column(nullable = false)
	private int expActuel;

	@Column(nullable = false)
	private int pvMax;

	@Column(nullable = false)
	private int pvActuel;
	
	@Column(nullable = false)
	private int chakraMax;
	
	@Column(nullable = false)
	private int chakraActuel;
	
	@Column(nullable = false)
	private int nbEchecs;

	@Column(nullable = false)
	private int nbReussites;

	@Column(nullable = false)
	private int argent;

	
	public Utilisateur() {
	}

	public Utilisateur(Integer id, String login, String password, String prenom, String nom, Genre genre,
			Specialite specialite, RangNinja rang, EtatNinja etatNinja, int niveau, int expActuel, int pvMax,
			int pvActuel, int chakraMax, int chakraActuel, int nbEchecs, int nbReussites, int argent) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.prenom = prenom;
		this.nom = nom;
		this.genre = genre;
		this.specialite = specialite;
		this.rang = rang;
		this.etatNinja = etatNinja;
		this.niveau = niveau;
		this.expActuel = expActuel;
		this.pvMax = pvMax;
		this.pvActuel = pvActuel;
		this.chakraMax = chakraMax;
		this.chakraActuel = chakraActuel;
		this.nbEchecs = nbEchecs;
		this.nbReussites = nbReussites;
		this.argent = argent;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public RangNinja getRang() {
		return rang;
	}

	public void setRang(RangNinja rang) {
		this.rang = rang;
	}

	public EtatNinja getEtatNinja() {
		return etatNinja;
	}

	public void setEtatNinja(EtatNinja etatNinja) {
		this.etatNinja = etatNinja;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public int getExpActuel() {
		return expActuel;
	}

	public void setExpActuel(int expActuel) {
		this.expActuel = expActuel;
	}

	public int getPvMax() {
		return pvMax;
	}

	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public int getPvActuel() {
		return pvActuel;
	}

	public void setPvActuel(int pvActuel) {
		this.pvActuel = pvActuel;
	}

	public int getChakraMax() {
		return chakraMax;
	}

	public void setChakraMax(int chakraMax) {
		this.chakraMax = chakraMax;
	}

	public int getChakraActuel() {
		return chakraActuel;
	}

	public void setChakraActuel(int chakraActuel) {
		this.chakraActuel = chakraActuel;
	}

	public int getNbEchecs() {
		return nbEchecs;
	}

	public void setNbEchecs(int nbEchecs) {
		this.nbEchecs = nbEchecs;
	}

	public int getNbReussites() {
		return nbReussites;
	}

	public void setNbReussites(int nbReussites) {
		this.nbReussites = nbReussites;
	}

	public int getArgent() {
		return argent;
	}

	public void setArgent(int argent) {
		this.argent = argent;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", login=" + login + ", password=" + password + ", prenom=" + prenom + ", nom="
				+ nom + ", genre=" + genre + ", specialite=" + specialite + ", rang=" + rang + ", niveau=" + niveau
				+ ", expActuel=" + expActuel + ", pvMax=" + pvMax + ", pvActuel=" + pvActuel + ", chakraMax="
				+ chakraMax + ", chakraActuel=" + chakraActuel + ", nbEchecs=" + nbEchecs + ", nbReussites="
				+ nbReussites + ", argent=" + argent + "]";
	}
	
	
}
