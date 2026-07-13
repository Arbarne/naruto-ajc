package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import naruto_backend.api.request.CreateEquipeRequest;
import naruto_backend.api.request.UpdateEquipeRequest;
import naruto_backend.dao.IDAOEquipe;
import naruto_backend.dao.IDAOMission;
import naruto_backend.dao.IDAOUtilisateur;
import naruto_backend.model.Equipe;
import naruto_backend.model.Hokage;
import naruto_backend.model.Leader;
import naruto_backend.model.Mission;
import naruto_backend.model.Ninja;
import naruto_backend.model.Utilisateur;

@Service
public class EquipeService {

	@Autowired
	IDAOEquipe daoEquipe;

	@Autowired
	IDAOUtilisateur daoUtilisateur;

	@Autowired
	IDAOMission daoMission;

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
	
	public Equipe insert(CreateEquipeRequest request, Authentication authentication)
	{
        Equipe equipe = new Equipe();

        Integer leaderId = request.getLeaderId();

        if (!estAdmin(authentication)) {
            // Un leader cree toujours sa propre equipe, jamais celle d'un autre
            Utilisateur user = daoUtilisateur.findByLogin(authentication.getName()).orElse(null);
            leaderId = user != null ? user.getId() : null;
        }

        if (leaderId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "leaderId requis");
        }

        Leader leader = (Leader) daoUtilisateur.findById(leaderId).orElse(null);

        if (leader != null && leader.getEquipe() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce leader dirige déjà une équipe");
        }

        equipe.setNom(request.getNom());
        equipe.setLeader(leader);

        Equipe equipeSauvegardee = daoEquipe.save(equipe);

        if (request.getNinjasId() != null) {
            for (Integer ninjaId : request.getNinjasId()) {
                if (daoUtilisateur.findById(ninjaId).orElse(null) instanceof Ninja ninja) {
                    ninja.setEquipe(equipeSauvegardee);
                    daoUtilisateur.save(ninja);
                }
            }
        }

        return equipeSauvegardee;
	}

	public Equipe update(Integer id, UpdateEquipeRequest request)
	{
        Equipe equipe = getById(id);

        Leader leader = (Leader) daoUtilisateur.findById(request.getLeaderId()).orElse(null);

        if (leader != null && leader.getEquipe() != null && !leader.getEquipe().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce leader dirige déjà une équipe");
        }

        equipe.setNom(request.getNom());
        equipe.setLeader(leader);

        return daoEquipe.save(equipe);
	}

	@Transactional
	public void delete(Integer id)
	{
		Equipe equipe = getById(id);

		if (equipe == null) {
			return;
		}

		// Les ninjas et missions rattaches doivent etre detaches avant de supprimer
		// l'equipe, sinon la contrainte de cle etrangere (RESTRICT) bloque la suppression.
		for (Ninja ninja : equipe.getNinjas()) {
			ninja.setEquipe(null);
			daoUtilisateur.save(ninja);
		}

		for (Mission mission : equipe.getMissions()) {
			mission.setEquipe(null);
			daoMission.save(mission);
		}

		daoEquipe.deleteEquipeById(id);
	}

    public Optional<Equipe> getByLeaderId(Integer leaderId) {
        return daoEquipe.findByLeaderId(leaderId);
    }

    public Equipe getEquipeDeUtilisateur(String login) {
        Utilisateur user = daoUtilisateur.findByLogin(login).orElse(null);

        if (user instanceof Leader leader) {
            return leader.getEquipe();
        }
        if (user instanceof Ninja ninja) {
            return ninja.getEquipe();
        }

        return null;
    }

    private boolean estAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean peutConsulter(Integer equipeId, String login) {
        Utilisateur user = daoUtilisateur.findByLogin(login).orElse(null);

        if (user instanceof Hokage) {
            return true;
        }

        Equipe equipe = getEquipeDeUtilisateur(login);
        return equipe != null && equipe.getId().equals(equipeId);
    }

    public boolean peutGererEquipe(Integer equipeId, Authentication authentication) {
        if (estAdmin(authentication)) {
            return true;
        }

        Equipe equipe = getEquipeDeUtilisateur(authentication.getName());
        return equipe != null && equipe.getId().equals(equipeId);
    }

    public boolean peutGererMembre(Integer membreId, Authentication authentication) {
        if (estAdmin(authentication)) {
            return true;
        }

        Utilisateur membre = daoUtilisateur.findById(membreId).orElse(null);
        Equipe equipe = null;

        if (membre instanceof Ninja ninja) {
            equipe = ninja.getEquipe();
        } else if (membre instanceof Leader leader) {
            equipe = leader.getEquipe();
        }

        return equipe != null && equipe.getLeader() != null
            && equipe.getLeader().getLogin().equals(authentication.getName());
    }

}
