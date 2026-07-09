package naruto_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import naruto_backend.model.Mission;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;

public interface IDAOMission extends JpaRepository<Mission, Integer> {

    List<Mission> findByRang(RangMission rang);

    List<Mission> findByStatut(StatutMission statut);
    
    public List<Mission> findByNomContaining(String nom);

    public List<Mission> findByEquipeId(Integer equipeId);

}
