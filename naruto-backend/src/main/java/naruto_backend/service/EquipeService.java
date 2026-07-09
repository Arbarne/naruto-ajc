<<<<<<< HEAD
package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naruto_backend.dao.IDAOEquipe;
import naruto_backend.model.Equipe;

@Service
public class EquipeService {

	@Autowired
	IDAOEquipe daoEquipe;
	
	public List<Equipe> getAll()
	{
		return daoEquipe.findAll();
	}
	
	public Equipe getById(Integer id) 
	{
		Optional<Equipe> opt = daoEquipe.findById(id);
		if(opt.isPresent()) {return opt.get();}
		else return null;
	}
	
	public void insert(Equipe equipe) 
	{
		daoEquipe.save(equipe);
	}
	
	public void update(Equipe equipe) 
	{
		daoEquipe.save(equipe);
	}

	public void delete(Integer id) 
	{
		daoEquipe.deleteById(id);
	}

    public Equipe findByLeaderId(Integer leaderId) {
        return daoEquipe.findByLeaderId(leaderId);
    }

}
=======
package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naruto_backend.api.request.CreateEquipeRequest;
import naruto_backend.api.request.UpdateEquipeRequest;
import naruto_backend.dao.IDAOEquipe;
import naruto_backend.dao.IDAOUtilisateur;
import naruto_backend.model.Equipe;
import naruto_backend.model.Leader;

@Service
public class EquipeService {

	@Autowired
	IDAOEquipe daoEquipe;

	@Autowired
	IDAOUtilisateur daoUtilisateur;
	
	public List<Equipe> getAll()
	{
		return daoEquipe.findAll();
	}
	
	public Equipe getById(Integer id) 
	{
		Optional<Equipe> opt = daoEquipe.findById(id);
		if(opt.isPresent()) {return opt.get();}
		else return null;
	}
	
	public Equipe insert(CreateEquipeRequest request) 
	{
        Equipe equipe = new Equipe();

        Leader leader = (Leader) daoUtilisateur.findById(request.getLeaderId()).orElse(null);

        equipe.setNom(request.getNom());
        equipe.setLeader(leader);

        return daoEquipe.save(equipe);
	}
	
	public Equipe update(Integer id, UpdateEquipeRequest request) 
	{
        Equipe equipe = getById(id);

        Leader leader = (Leader) daoUtilisateur.findById(request.getLeaderId()).orElse(null);

        equipe.setNom(request.getNom());
        equipe.setLeader(leader);

        return daoEquipe.save(equipe);
	}

	public void delete(Integer id) 
	{
		daoEquipe.deleteById(id);
	}

    public Equipe findByLeaderId(Integer leaderId) {
        return daoEquipe.findByLeaderId(leaderId);
    }

}
>>>>>>> 8555a0d484e9cfa17048c93e67aed0796dd543b1
