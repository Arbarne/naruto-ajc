package naruto_backend.api.response;

import java.util.List;

import naruto_backend.model.Equipe;


public class EquipeDetailsResponse {

    private Integer id;
    private String nom;
    private Integer leaderId;
    private String leaderNom;
    private List<NinjaOptionResponse> ninjas;

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
    public Integer getLeaderId() {
        return leaderId;
    }
    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }
    public String getLeaderNom() {
        return leaderNom;
    }
    public void setLeaderNom(String leaderNom) {
        this.leaderNom = leaderNom;
    }
    public List<NinjaOptionResponse> getNinjas() {
        return ninjas;
    }
    public void setNinjas(List<NinjaOptionResponse> ninjas) {
        this.ninjas = ninjas;
    }

    public static EquipeDetailsResponse convert(Equipe equipe) {
        EquipeDetailsResponse response = new EquipeDetailsResponse();
        response.setId(equipe.getId());
        response.setNom(equipe.getNom());
        response.setLeaderId(equipe.getLeader().getId());
        response.setLeaderNom(equipe.getLeader().getNom());
        response.setNinjas(
            equipe.getNinjas()
            .stream()
            .map(ninja -> new NinjaOptionResponse(
                      ninja.getId(),
                      ninja.getLogin(),
                      ninja.getNom(),
                      ninja.getPrenom(),
                      ninja.getRang() != null ? ninja.getRang().toString() : null,
                      ninja.getSpecialite() != null ? ninja.getSpecialite().toString() : null,
                      ninja.getEquipe() != null ? ninja.getEquipe().getId() : null
                  ))
                  .toList());
        return response;
    }


}
