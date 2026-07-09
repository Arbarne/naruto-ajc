package naruto_backend.model;

public class EquipeBasicInfo {
    private Integer id;

    private String nom;
    
    public EquipeBasicInfo() {
    }

    public EquipeBasicInfo(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }


    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public String toString() {
        return "Equipe [id=" + id + ", nom=" + nom + "]";
    }
    
}
