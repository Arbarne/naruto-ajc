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
