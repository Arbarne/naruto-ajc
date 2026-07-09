package naruto_backend.api.response;

import naruto_backend.model.Equipe;
import naruto_backend.model.EquipeBasicInfo;
import naruto_backend.model.EtatNinja;
import naruto_backend.model.Genre;
import naruto_backend.model.Leader;
import naruto_backend.model.Ninja;
import naruto_backend.model.RangNinja;
import naruto_backend.model.Specialite;
import naruto_backend.model.Utilisateur;

public class UtilisateurDetailsResponse {

	private String login;
	private String nom;
	private String prenom;
	private Genre genre;
	private Specialite specialite; 
	private RangNinja rang; 
	private EtatNinja etatNinja;     	  
	private int niveau;
	private int expActuel;
	private int pvMax;
	private int pvActuel;
	private int chakraMax;
	private int chakraActuel;
	private int nbEchecs;
	private int nbReussites;
	private int argent;
	private EquipeBasicInfo equipe;

	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
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

	public EquipeBasicInfo getEquipeBasicInfo() {
		return equipe;
	}


	public void setEquipeBasicInfo(EquipeBasicInfo equipeBasicInfo) {
		this.equipe = equipeBasicInfo;
	}

	public static UtilisateurDetailsResponse convert(Utilisateur utilisateur) {
        UtilisateurDetailsResponse response = new UtilisateurDetailsResponse();

        response.equipe = new EquipeBasicInfo();

        response.setLogin(utilisateur.getLogin());
        response.setNom(utilisateur.getNom());
        response.setPrenom(utilisateur.getPrenom());
        response.setGenre(utilisateur.getGenre());
        response.setNiveau(utilisateur.getNiveau());
        if (utilisateur instanceof Ninja ninja) {
            response.getEquipeBasicInfo().setId(ninja.getEquipe().getId());
            response.getEquipeBasicInfo().setNom(ninja.getEquipe().getNom());
        } else if (utilisateur instanceof Leader leader)  {
            response.getEquipeBasicInfo().setId(leader.getEquipe().getId());
            response.getEquipeBasicInfo().setNom(leader.getEquipe().getNom());
        }
        else {
            response.getEquipeBasicInfo().setId(-1);
            response.getEquipeBasicInfo().setNom(null);
        }
        response.setRang(utilisateur.getRang());
        response.setEtatNinja(utilisateur.getEtat());
        response.setSpecialite(utilisateur.getSpecialite());
        response.setPvMax(utilisateur.getPvMax());
        response.setPvActuel(utilisateur.getPvActuel());
        response.setChakraMax(utilisateur.getChakraMax());
        response.setChakraActuel(utilisateur.getChakraActuel());
        response.setNbEchecs(utilisateur.getNbEchecs());
        response.setNbReussites(utilisateur.getNbReussites());
        response.setArgent(utilisateur.getArgent());

        return response;
    }
}
