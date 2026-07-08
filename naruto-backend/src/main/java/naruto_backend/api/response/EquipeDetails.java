package naruto_backend.api.response;

import java.util.List;

import naruto_backend.model.Equipe;


public class EquipeDetails {

    private Integer id;
    private String nom;
    private Integer leaderId;
    private String leaderNom;
    private List<MiniNinja> ninjas;
    
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
    public List<MiniNinja> getNinjas() {
        return ninjas;
    }
    public void setNinjas(List<MiniNinja> ninjas) {
        this.ninjas = ninjas;
    }

    public static EquipeDetails convert(Equipe equipe) {
        EquipeDetails response = new EquipeDetails();
        response.setId(equipe.getId());
        response.setNom(equipe.getNom());
        response.setLeaderId(equipe.getLeader().getId());
        response.setLeaderNom(equipe.getLeader().getNom());
        response.setNinjas(
            equipe.getNinjas()
            .stream()
            .map(ninja -> new MiniNinja(
                      ninja.getId(),
                      ninja.getNom()
                  ))
                  .toList());
        return response;
    }
    

}
