package naruto_backend.api.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class CreateEquipeRequest {

    @NotBlank
    private String nom;

    // Optionnel : seul un Hokage peut le renseigner pour creer une equipe au nom d'un autre leader
    // Pour un Leader, l'id est toujours resolu depuis l'utilisateur authentifie (voir EquipeService.insert)
    private Integer leaderId;

    // Optionnel : ninjas a assigner directement a la creation de l'equipe (Hokage uniquement)
    private List<Integer> ninjasId;

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

    public List<Integer> getNinjasId() {
        return ninjasId;
    }

    public void setNinjasId(List<Integer> ninjasId) {
        this.ninjasId = ninjasId;
    }

}
