package naruto_backend.api.response;

import java.time.LocalDate;

import naruto_backend.model.Mission;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;

public class MissionDetailsResponse {

    private Integer id;
    private String nom;
    private String description;
    private RangMission rang;
    private int gainExp;
    private int recompense;
    private Integer equipeId;
    private String equipeNom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutMission statut;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    public Integer getEquipeId() {
        return equipeId;
    }
    public void setEquipeId(Integer equipeId) {
        this.equipeId = equipeId;
    }
    public String getEquipeNom() {
        return equipeNom;
    }
    public void setEquipeNom(String equipeNom) {
        this.equipeNom = equipeNom;
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

    public static MissionDetailsResponse convert (Mission mission) {
        MissionDetailsResponse response = new MissionDetailsResponse();

        response.setId(mission.getId());
        response.setNom(mission.getNom());
        response.setDescription(mission.getDescription());
        response.setRang(mission.getRang());
        response.setGainExp(mission.getGainExp());
        response.setRecompense(mission.getRecompense());
        response.setEquipeId(mission.getEquipe() != null ? mission.getEquipe().getId() : null);
        response.setEquipeNom(mission.getEquipe() != null ? mission.getEquipe().getNom() : null);
        response.setDateDebut(mission.getDateDebut());
        response.setDateFin(mission.getDateFin());
        response.setStatut(mission.getStatut());

        return response;
    }

}
