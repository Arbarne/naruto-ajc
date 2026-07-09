package naruto_backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import naruto_backend.model.EtatNinja;
import naruto_backend.model.Hokage;
import naruto_backend.model.Leader;
import naruto_backend.model.Ninja;
import naruto_backend.model.RangNinja;
import naruto_backend.model.Specialite;
import naruto_backend.model.Utilisateur;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer> {
	@Query("FROM Ninja")
	public List<Ninja> findAllNinja();

	@Query("FROM Leader")
	public List<Leader> findAllLeader();

	@Query("FROM Hokage")
	public List<Hokage> findAllHokage();
	
	public Utilisateur findByLogin(String login);

	//maybe a supprimer si inutilisé
	public Utilisateur findByLoginAndPassword(String login,String password);

    public List<Utilisateur> findBySpecialite(Specialite specialite);

    public List<Utilisateur> findByRang(RangNinja rang);

    public List<Utilisateur> findByEtat(EtatNinja etat);
}
