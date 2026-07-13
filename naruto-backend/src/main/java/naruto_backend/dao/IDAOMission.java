package naruto_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import naruto_backend.model.Mission;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;

public interface IDAOMission extends JpaRepository<Mission, Integer> {

    List<Mission> findByRang(RangMission rang);

    List<Mission> findByStatut(StatutMission statut);
    
    public List<Mission> findByNomContaining(String nom);

    public List<Mission> findByEquipeId(Integer equipeId);

    @Query("select m from Mission m where m.equipe.leader.id = :leaderId and m.statut in ('EnCours', 'Disponible')")
    public Optional<Mission> findCurrentMissionByLeaderId(@Param("leaderId") Integer leaderId);

    @Query("SELECT m FROM Mission m JOIN Ninja n ON n.equipe.id = m.equipe.id WHERE n.id = :ninjaId AND m.statut in ('EnCours', 'Disponible')")
    public Optional<Mission> findCurrentMissionByNinjaId(@Param("ninjaId") Integer ninjaId);

}
