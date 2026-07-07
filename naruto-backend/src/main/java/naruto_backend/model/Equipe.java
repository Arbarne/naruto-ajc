package naruto_backend.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "team")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "leader")
    @Column(name = "leader", nullable = false)
    private Leader leader;

    @OneToMany(mappedBy = "equipe")
    private List<Ninja> ninjas;

    @OneToMany(mappedBy = "equipe")
    private List<Mission> missions;

    
    public Equipe() {
    }

    public Equipe(Integer id, Leader leader, List<Ninja> ninjas, List<Mission> missions) {
        this.id = id;
        this.leader = leader;
        this.ninjas = ninjas;
        this.missions = missions;
    }


    public Integer getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public List<Ninja> getNinjas() {
        return ninjas;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void setNinjas(List<Ninja> ninjas) {
        this.ninjas = ninjas;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }


    @Override
    public String toString() {
        return "Equipe [id=" + id + ", leader=" + leader + ", ninjas=" + ninjas + ", missions=" + missions + "]";
    }
    
}
