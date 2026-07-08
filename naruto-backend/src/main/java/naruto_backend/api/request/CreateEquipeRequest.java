package naruto_backend.api.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateEquipeRequest {

    @NotBlank
    private String nom;

    @NotNull
    private Integer leaderId;

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
