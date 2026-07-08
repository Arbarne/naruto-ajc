package naruto_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naruto_backend.api.request.CreateUtilisateurRequest;
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

	public Utilisateur getByLogin(String login)
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


	public List<Utilisateur> getByRangNinja(RangNinja rangNinja) {
		return daoUtilisateur.findByRangNinja(rangNinja);
	}

	
	public List<Utilisateur> getByEtatNinja(EtatNinja etatNinja) {
		return daoUtilisateur.findByEtatNinja(etatNinja);
	}

	public Utilisateur insert(CreateUtilisateurRequest request) 
	{
		Utilisateur utilisateur = this.save(new Utilisateur(), request);

		return utilisateur;
	}
	
	public Utilisateur update(int id, UpdateUtilisateurRequest request) 
	{
		Utilisateur utilisateur = this.getById(id);
		utilisateur = this.save(utilisateur, request);
		return utilisateur;
	}

	public void delete(Integer id) 
	{
		daoUtilisateur.deleteById(id);
	}
	
    private Utilisateur save(Utilisateur utilisateur, CreateUtilisateurRequest request) {
    	Integer argent = 0;
    	Integer nbReussite = 0;
    	Integer nbEchec = 0;
    	Integer niveau = 1;
    	Integer expActuel = 0;

    	// A changer selon l'equilibrage
    	Integer pvMax = 100;
    	Integer chakraMax = 100;

    	utilisateur.setLogin(request.getLogin());
        utilisateur.setPassword(request.getPassword());
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
		utilisateur.setGenre(request.getGenre());

		utilisateur.setSpecialite(Specialite.Aucune);
		utilisateur.setRang(RangNinja.Genin);
		utilisateur.setEtatNinja(EtatNinja.Disponible);
		utilisateur.setNiveau(niveau);
		utilisateur.setExpActuel(expActuel);
		utilisateur.setPvMax(pvMax);
		utilisateur.setPvActuel(utilisateur.getPvMax());
		utilisateur.setChakraMax(chakraMax);
		utilisateur.setChakraActuel(utilisateur.getChakraMax());
		utilisateur.setNbEchecs(nbEchec);
		utilisateur.setNbReussites(nbReussite);
		utilisateur.setArgent(argent);
        return this.daoUtilisateur.save(utilisateur);
    }
    
    private Utilisateur save(Utilisateur utilisateur, UpdateUtilisateurRequest request) {
        utilisateur.setLogin(request.getLogin());
        utilisateur.setPassword(request.getPassword());
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
		utilisateur.setGenre(request.getGenre());
		utilisateur.setSpecialite(request.getSpecialite());
		utilisateur.setRang(request.getRang());
		utilisateur.setEtatNinja(request.getEtatNinja());
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
    
}
