package naruto_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import naruto_backend.model.Equipe;

public interface IDAOEquipe extends JpaRepository<Equipe, Integer> {
    public Equipe findByLeaderId (Integer leaderId);

}
