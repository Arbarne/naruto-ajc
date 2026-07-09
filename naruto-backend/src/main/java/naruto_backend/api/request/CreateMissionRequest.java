package naruto_backend.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import naruto_backend.model.RangMission;

public class CreateMissionRequest {

    @NotBlank
    private String nom;

    @NotBlank
    private String description;

    @NotNull
    private RangMission rang;

    @NotNull
    private int gainExp;

    @NotNull
    private int recompense;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RangMission getRang() {
        return rang;
    }

    public void setRang(RangMission rang) {
        this.rang = rang;
    }

    public int getGainExp() {
        return gainExp;
    }

    public void setGainExp(int gainExp) {
        this.gainExp = gainExp;
    }

    public int getRecompense() {
        return recompense;
    }

    public void setRecompense(int recompense) {
        this.recompense = recompense;
    }

}
