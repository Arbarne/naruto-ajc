package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import naruto_backend.api.request.SubscriptionRequest;
import naruto_backend.api.request.UpdateUtilisateurRequest;
import naruto_backend.dao.IDAOUtilisateur;
import naruto_backend.model.EtatNinja;
import naruto_backend.model.Hokage;
import naruto_backend.model.Leader;
import naruto_backend.model.Ninja;
import naruto_backend.model.RangNinja;
import naruto_backend.model.Specialite;
import naruto_backend.model.Utilisateur;

@Service
public class UtilisateurService {

    @Autowired
    IDAOUtilisateur daoUtilisateur;

	private final PasswordEncoder passwordEncoder;

	private final EquipeService equipeService;

	public UtilisateurService(
        IDAOUtilisateur daoUtilisateur,
        PasswordEncoder passwordEncoder, EquipeService equipeService) {
    this.daoUtilisateur = daoUtilisateur;
    this.passwordEncoder = passwordEncoder;
	this.equipeService = equipeService;
}

    public List<Utilisateur> getAll()
	{
		return daoUtilisateur.findAll();
	}

    public List<Ninja> getAllNinja()
	{
		return daoUtilisateur.findAllNinja();
	}

    public List<Leader> getAllLeader()
	{
		return daoUtilisateur.findAllLeader();
	}

    public List<Hokage> getAllHokage()
	{
		return daoUtilisateur.findAllHokage();
	}
	
	public Utilisateur getById(Integer id) 
	{
		Optional<Utilisateur> opt = daoUtilisateur.findById(id);
		if(opt.isPresent()) {return opt.get();}
		else return null;
	}

	public Ninja getNinjaById(Integer id) 
	{
		return (Ninja) daoUtilisateur.findById(id).orElse(null);
	}

	public Leader getLeaderById(Integer id) 
	{
		return (Leader) daoUtilisateur.findById(id).orElse(null);
	}

	public Hokage getHokageById(Integer id) 
	{
		return (Hokage) daoUtilisateur.findById(id).orElse(null);
	}

	public Optional<Utilisateur> getByLogin(String login)
	{
		return daoUtilisateur.findByLogin(login);
	}

	//maybe a supprimer si inutilisé
	public Utilisateur getByLoginAndPassword(String login,String password)
	{
		return daoUtilisateur.findByLoginAndPassword(login, password);
	}
	

	public List<Utilisateur> getBySpecialite(Specialite specialite) {
		return daoUtilisateur.findBySpecialite(specialite);
	}


	public List<Utilisateur> getByRang(RangNinja rang) {
		return daoUtilisateur.findByRang(rang);
	}

	
	public List<Utilisateur> getByEtat(EtatNinja etat) {
		return daoUtilisateur.findByEtat(etat);
	}

	public Utilisateur update(int id, UpdateUtilisateurRequest request)
	{
		Utilisateur utilisateur = this.getById(id);
		utilisateur = this.save(utilisateur, request);
		return utilisateur;
	}

	public void delete(Integer id)
	{
		Utilisateur utilisateur = this.getById(id);

		if (utilisateur instanceof Leader leader && leader.getEquipe() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Ce leader dirige une équipe : réassignez-la ou supprimez-la d'abord");
		}

		daoUtilisateur.deleteById(id);
	}
	
	public Utilisateur create(Utilisateur utilisateur, SubscriptionRequest request) {
    	Integer argent = 0;
    	Integer nbReussite = 0;
    	Integer nbEchec = 0;
    	Integer niveau = 1;
    	Integer expActuel = 0;

    	// A changer selon l'equilibrage
    	Integer pvMax = 100;
		Integer pvActuel = 0;
    	Integer chakraMax = 100;
		Integer chakraActuel = 0;

    	utilisateur.setLogin(request.getLogin());
        utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
		utilisateur.setGenre(request.getGenre());

		utilisateur.setSpecialite(Specialite.Aucune);
		utilisateur.setRang(RangNinja.Genin);
		utilisateur.setEtat(EtatNinja.Disponible);
		utilisateur.setNiveau(niveau);
		utilisateur.setExpActuel(expActuel);
		utilisateur.setPvMax(pvMax);
		utilisateur.setPvActuel(pvActuel);
		utilisateur.setChakraMax(chakraMax);
		utilisateur.setChakraActuel(chakraActuel);
		utilisateur.setNbEchecs(nbEchec);
		utilisateur.setNbReussites(nbReussite);
		utilisateur.setArgent(argent);
        return this.daoUtilisateur.save(utilisateur);
    }
    
	private Utilisateur save(Utilisateur utilisateur, UpdateUtilisateurRequest request) {
        utilisateur.setLogin(request.getLogin());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));
        }
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
		utilisateur.setGenre(request.getGenre());
		utilisateur.setSpecialite(request.getSpecialite());
		utilisateur.setRang(request.getRang());
		utilisateur.setEtat(request.getEtat());
		utilisateur.setNiveau(request.getNiveau());
		utilisateur.setExpActuel(request.getExpActuel());
		utilisateur.setPvMax(request.getPvMax());
		utilisateur.setPvActuel(request.getPvActuel());
		utilisateur.setChakraMax(request.getChakraMax());
		utilisateur.setChakraActuel(request.getChakraActuel());
		utilisateur.setNbEchecs(request.getNbEchecs());
		utilisateur.setNbReussites(request.getNbReussites());
		utilisateur.setArgent(request.getArgent());

        return this.daoUtilisateur.save(utilisateur);
    }

	//Méthode simple pour save un utilisateur
	public Utilisateur insert(Utilisateur utilisateur) {
    	return this.daoUtilisateur.save(utilisateur);
	}

	public Utilisateur updateEquipe(Integer id, Integer equipeId) throws NotFoundException{
		Utilisateur user = daoUtilisateur.findById(id).orElseThrow(NotFoundException::new);

		if (user instanceof Ninja ninja) {
            ninja.setEquipe(equipeService.getById(Integer.valueOf(equipeId)));
        }
        else if (user instanceof Leader leader) {
            leader.setEquipe(equipeService.getById(Integer.valueOf(equipeId)));
        }
		
		user = daoUtilisateur.save(user);
		return user;
	}

	public Utilisateur deleteUserFromEquipe(Integer id) throws NotFoundException{

		Utilisateur user = this.daoUtilisateur.findById(id).orElseThrow(NotFoundException::new);

        if (user instanceof Ninja ninja) {
            ninja.setEquipe(null);
        }
        else if (user instanceof Leader leader) {
            leader.setEquipe(null);
        }

		user = daoUtilisateur.save(user);
		return user;
	}
    
}
