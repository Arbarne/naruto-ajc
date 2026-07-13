package naruto_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	public Optional<Utilisateur> findByLogin(String login);

	//maybe a supprimer si inutilisé
	public Utilisateur findByLoginAndPassword(String login,String password);

    public List<Utilisateur> findBySpecialite(Specialite specialite);

    public List<Utilisateur> findByRang(RangNinja rang);

    public List<Utilisateur> findByEtat(EtatNinja etat);

	@Query("SELECT l FROM Leader l WHERE l.equipe IS NOT NULL AND l.equipe NOT IN (SELECT m.equipe FROM Mission m WHERE m.statut = 'EnCours')")
	public List<Leader> findAvailabLeaders();

	// Le discriminateur d'heritage (user_type) n'est pas un champ mappe : seule une requete
	// native peut le modifier directement, sans passer par le cycle de vie JPA de l'entite.
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE utilisateur SET user_type = :type WHERE id = :id", nativeQuery = true)
	public void updateUserType(@Param("id") Integer id, @Param("type") String type);

}
