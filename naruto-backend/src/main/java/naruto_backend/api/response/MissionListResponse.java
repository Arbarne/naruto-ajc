package naruto_backend.api.response;

import naruto_backend.model.Mission;

public class MissionListResponse {

    private Integer id;
    private String nom;
    private String description;
    private String rang;
    private String equipeNom;
    
    
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

    public static MissionListResponse convert (Mission mission) {
        MissionListResponse response = new MissionListResponse();

        response.setId(mission.getId());
        response.setNom(mission.getNom());
        response.setDescription(mission.getDescription());
        response.setRang(mission.getRang().toString());
        response.setEquipeNom(mission.getEquipe().getNom());

        return response;
    }

}
