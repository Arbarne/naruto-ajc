package naruto_backend.api.response;

import naruto_backend.model.Equipe;

public class EquipeList {

    private Integer id;
    private String nom;
    private String leaderNom;


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

    public String getLeaderNom() {
        return leaderNom;
    }

    public void setLeaderNom(String leaderNom) {
        this.leaderNom = leaderNom;
    }

    public static EquipeList convert (Equipe equipe) {
        EquipeList response = new EquipeList();

        response.setId(equipe.getId());
        response.setNom(equipe.getNom());
        response.setLeaderNom(equipe.getLeader().getNom());

        return response;
    }

}
