package naruto_backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naruto_backend.api.request.AssignMissionRequest;
import naruto_backend.api.request.CreateMissionRequest;
import naruto_backend.api.request.StartMissionRequest;
import naruto_backend.api.request.UpdateMissionRequest;
import naruto_backend.dao.IDAOEquipe;
import naruto_backend.dao.IDAOMission;
import naruto_backend.dao.IDAOUtilisateur;
import naruto_backend.model.Equipe;
import naruto_backend.model.EtatNinja;
import naruto_backend.model.Leader;
import naruto_backend.model.Mission;
import naruto_backend.model.Ninja;
import naruto_backend.model.RangMission;
import naruto_backend.model.StatutMission;
import naruto_backend.model.Utilisateur;

@Service
public class MissionService {

    @Autowired
    IDAOMission daoMission;

	@Autowired
	IDAOEquipe daoEquipe;

    @Autowired
    IDAOUtilisateur daoUtilisateur;

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
        mission.setStatut(StatutMission.Disponible);

        return daoMission.save(mission);
	}
	
	public Mission update(Integer id, UpdateMissionRequest request) 
	{
		Mission mission = getById(id);
		StatutMission statut = request.getStatut();

		mission.setNom(request.getNom());
        mission.setDescription(request.getDescription());
        mission.setRang(request.getRang());
        mission.setGainExp(request.getGainExp());
        mission.setRecompense(request.getRecompense());
        mission.setEquipe(daoEquipe.findById(request.getEquipeId()).orElse(null));
        mission.setDateDebut(request.getDateDebut());
        mission.setStatut(statut);

        // Une mission cloturee (succes, echec ou annulation) prend automatiquement la date du jour
        boolean estCloturee = statut == StatutMission.Terminee
            || statut == StatutMission.Echouee
            || statut == StatutMission.Annulee;

        mission.setDateFin(estCloturee ? LocalDate.now() : request.getDateFin());

		return daoMission.save(mission);
	}

    public Mission assignMission(AssignMissionRequest request) {
        Mission mission = getById(request.getId());

        Equipe equipe = daoEquipe.findById(request.getEquipeId()).orElse(null);

        mission.setEquipe(equipe);
        mission.setStatut(StatutMission.Disponible);

        return daoMission.save(mission);
    }

    public Mission startMission(Integer id) {
        Mission mission = getById(id);

        mission.setDateDebut(LocalDate.now());
        mission.setStatut(StatutMission.EnCours);

        mission.getEquipe().getLeader().setEtat(EtatNinja.EnMission);
        daoUtilisateur.save(mission.getEquipe().getLeader());

        for (Ninja ninja : mission.getEquipe().getNinjas()) {
            ninja.setEtat(EtatNinja.EnMission);
            daoUtilisateur.save(ninja);
        }

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

    public Mission getMissionDeUtilisateur(String login) {
        Utilisateur user = daoUtilisateur.findByLogin(login).orElse(null);

        if (user instanceof Leader leader) {
            return daoMission.findCurrentMissionByLeaderId(leader.getId()).orElse(null);
        } else if (user instanceof Ninja ninja) {
            return daoMission.findCurrentMissionByNinjaId(ninja.getId()).orElse(null);
        }

        return null;
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

    public Mission terminerMission(Integer id) {
        Mission mission = getById(id);

        if (mission == null) return null;

        mission.setStatut(StatutMission.Terminee);
        mission.setDateFin(LocalDate.now());

        mission.getEquipe().getLeader().setEtat(EtatNinja.Disponible);
        daoUtilisateur.save(mission.getEquipe().getLeader());

        for (Ninja ninja : mission.getEquipe().getNinjas()) {
            ninja.setEtat(EtatNinja.Disponible);
            daoUtilisateur.save(ninja);
        }

        return daoMission.save(mission);
    }
}
