package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import naruto_backend.api.request.AssignMissionRequest;
import naruto_backend.api.request.CreateEquipeRequest;
import naruto_backend.api.request.CreateMissionRequest;
import naruto_backend.api.request.UpdateEquipeRequest;
import naruto_backend.api.request.UpdateMissionRequest;
import naruto_backend.dao.IDAOEquipe;
import naruto_backend.dao.IDAOMission;
import naruto_backend.model.Equipe;
import naruto_backend.model.Mission;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;

@Service
public class MissionService {

    @Autowired
    IDAOMission daoMission;

	@Autowired
	IDAOEquipe daoEquipe;

    public List<Mission> getAll()
	{
		return daoMission.findAll();
	}
	
	public Mission getById(Integer id) 
	{
		Optional<Mission> opt = daoMission.findById(id);
		if(opt.isPresent()) {return opt.get();}
		else return null;
	}
	
	public Mission insert(CreateMissionRequest request) 
	{
		Mission mission = new Mission();

		mission.setNom(request.getNom());
        mission.setDescription(request.getDescription());
        mission.setRang(request.getRang());
        mission.setGainExp(request.getGainExp());
        mission.setRecompense(request.getRecompense());

        return daoMission.save(mission);
	}
	
	public Mission update(Integer id, UpdateMissionRequest request) 
	{
		Mission mission = getById(id);

		mission.setNom(request.getNom());
        mission.setDescription(request.getDescription());
        mission.setRang(request.getRang());
        mission.setGainExp(request.getGainExp());
        mission.setRecompense(request.getRecompense());
        mission.setDateDebut(request.getDateDebut());
        mission.setDateFin(request.getDateFin());
        mission.setStatut(request.getStatut());		

		return daoMission.save(mission);
	}

    public Mission assignMission(AssignMissionRequest request) {
        Mission mission = getById(request.getId());

        Equipe equipe = daoEquipe.findById(request.getEquipeId()).orElse(null);

        mission.setEquipe(equipe);
        mission.setStatut(StatutMission.EnCours);

        return daoMission.save(mission);
    }

	public void delete(Integer id) 
	{
		daoMission.deleteById(id);
	}

    public List<Mission> findByRang(String rang) {
        return daoMission.findByRang(RangMission.valueOf(rang));
    }

    public List<Mission> findByStatut(String statut) {
        return daoMission.findByStatut(StatutMission.valueOf(statut));
    }

    public List<Mission> findByNomContaining(String nom) {
        return daoMission.findByNomContaining(nom);
    }

    public List<Mission> findByEquipeId(Integer equipeId) {
        return daoMission.findByEquipeId(equipeId);
    }

}
