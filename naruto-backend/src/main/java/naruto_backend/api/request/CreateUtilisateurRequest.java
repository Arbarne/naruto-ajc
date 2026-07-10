package naruto_backend.api.request;

import naruto_backend.model.Genre;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class CreateUtilisateurRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
  private String nom;

    @NotBlank
  private String prenom;

    @NotNull
  private Genre genre;

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

  
}
