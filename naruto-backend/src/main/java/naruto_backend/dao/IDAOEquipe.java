package naruto_backend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import naruto_backend.model.Equipe;

public interface IDAOEquipe extends JpaRepository<Equipe, Integer> {
    public Optional<Equipe> findByLeaderId (Integer leaderId);

}
