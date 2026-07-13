package naruto_backend.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import naruto_backend.model.EtatNinja;
import naruto_backend.model.Genre;
import naruto_backend.model.RangNinja;
import naruto_backend.model.Specialite;

public class UpdateUtilisateurRequest {
    @NotBlank
	private String login;

	// Optionnel : laisser vide pour ne pas changer le mot de passe existant
	private String password;

    @NotBlank
	private String nom;

    @NotBlank
	private String prenom;

    @NotNull
	private Genre genre;

  
    @NotNull
	private Specialite specialite; 

    @NotNull
	private RangNinja rang; 

    @NotNull
	private EtatNinja etat; 
  
    @NotNull
    @Positive
	private int niveau;
	
    @NotNull
    @PositiveOrZero
	private int expActuel;

    @NotNull
    @Positive
	private int pvMax;

    @NotNull
    @PositiveOrZero
	private int pvActuel;

    @NotNull
    @Positive
	private int chakraMax;

    @NotNull
    @PositiveOrZero
	private int chakraActuel;

    @NotNull
    @PositiveOrZero
	private int nbEchecs;

    @NotNull
    @PositiveOrZero
	private int nbReussites;

    @NotNull
    @PositiveOrZero
	private int argent;

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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
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

	public EtatNinja getEtat() {
		return etat;
	}

	public void setEtat(EtatNinja etat) {
		this.etat = etat;
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
	
}
