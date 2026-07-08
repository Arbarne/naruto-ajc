package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naruto_backend.dao.IDAOMission;
import naruto_backend.model.Mission;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;

@Service
public class MissionService {

    @Autowired
    IDAOMission daoMission;

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
	
	public void insert(Mission mission) 
	{
		daoMission.save(mission);
	}
	
	public void update(Mission mission) 
	{
		daoMission.save(mission);
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
