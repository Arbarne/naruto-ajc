package naruto_backend.api.response;

import naruto_backend.model.EtatNinja;
import naruto_backend.model.Genre;
import naruto_backend.model.RangNinja;
import naruto_backend.model.Specialite;
import naruto_backend.model.Utilisateur;

public class UtilisateurListViewResponse {

	private String login;
	private String nom;
	private String prenom;
	private Genre genre;
	private Specialite specialite;
	private RangNinja rang;
	private EtatNinja etatNinja;

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



	public static UtilisateurListViewResponse convert(Utilisateur utilisateur) {
        UtilisateurListViewResponse response = new UtilisateurListViewResponse();

        response.setLogin(utilisateur.getLogin());
        response.setNom(utilisateur.getNom());
        response.setPrenom(utilisateur.getPrenom());
        response.setGenre(utilisateur.getGenre());
        response.setRang(utilisateur.getRang());
        response.setEtatNinja(utilisateur.getEtatNinja());
        response.setSpecialite(utilisateur.getSpecialite());
        return response;
    }
}
