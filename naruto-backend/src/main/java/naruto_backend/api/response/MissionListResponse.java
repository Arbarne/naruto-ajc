package naruto_backend.api.response;

import java.time.LocalDate;

import naruto_backend.model.Mission;

public class MissionListResponse {

    private Integer id;
    private String nom;
    private String description;
    private String rang;
    private String equipeNom;
    private String statut;
    private LocalDate dateFin;
    
    
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
    public String getRang() {
        return rang;
    }
    public void setRang(String rang) {
        this.rang = rang;
    }
    public String getEquipeNom() {
        return equipeNom;
    }
    public void setEquipeNom(String equipeNom) {
        this.equipeNom = equipeNom;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    
    public static MissionListResponse convert (Mission mission) {
        MissionListResponse response = new MissionListResponse();

        response.setId(mission.getId());
        response.setNom(mission.getNom());
        response.setDescription(mission.getDescription());
        response.setRang(mission.getRang().toString());
        response.setEquipeNom(mission.getEquipe().getNom());
        response.setStatut(mission.getStatut().toString());

        return response;
    }

}
