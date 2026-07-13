package naruto_backend.api.request;

import jakarta.validation.constraints.NotBlank;

public class CreateEquipeRequest {

    @NotBlank
    private String nom;

    // Optionnel : seul un Hokage peut le renseigner pour creer une equipe au nom d'un autre leader.
    // Pour un Leader, l'id est toujours resolu depuis l'utilisateur authentifie (voir EquipeService.insert).
    private Integer leaderId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

}
