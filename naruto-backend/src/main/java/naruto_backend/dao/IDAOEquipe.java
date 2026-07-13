package naruto_backend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import naruto_backend.model.Equipe;

public interface IDAOEquipe extends JpaRepository<Equipe, Integer> {
    public Optional<Equipe> findByLeaderId (Integer leaderId);

    // deleteById() classique declenche une TransientPropertyValueException a cause de la relation
    // bidirectionnelle @OneToOne(mappedBy="leader") : une requete de suppression directe l'evite.
    // flushAutomatically force le flush des entites modifiees (ex: ninja detache) AVANT ce DELETE,
    // sinon la requete bulk s'execute sur un etat de base pas encore a jour et la contrainte
    // de cle etrangere (RESTRICT) bloque la suppression.
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM Equipe e WHERE e.id = :id")
    void deleteEquipeById(@Param("id") Integer id);

}
