package naruto_backend.api.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;

public class UpdateMissionRequest {

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

    @NotNull
    private int equipeId;

    @NotNull
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    @NotNull
    private StatutMission statut;

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

    public int getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(int equipeId) {
        this.equipeId = equipeId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public StatutMission getStatut() {
        return statut;
    }

    public void setStatut(StatutMission statut) {
        this.statut = statut;
    }

}
