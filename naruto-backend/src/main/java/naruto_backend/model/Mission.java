package naruto_backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", length = 35, nullable = false)    
    private String nom;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "rang", length = 5,nullable = false, unique = true)
    private RangMission rang;

    @Column(name = "gain_exp", length = 50, nullable = false)
    private int gainExp;

    @Column(name = "recompense", length = 50, nullable = false)
    private int recompense;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 20, nullable = false)
    private StatutMission statut;
    
    
    public Mission() {
    }

    public Mission(Integer id, String nom, String description, RangMission rang, int gainExp, int recompense,
            Equipe equipe, LocalDate dateDebut, LocalDate dateFin, StatutMission statut) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.rang = rang;
        this.gainExp = gainExp;
        this.recompense = recompense;
        this.equipe = equipe;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }


    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public RangMission getRang() {
        return rang;
    }

    public int getGainExp() {
        return gainExp;
    }

    public int getRecompense() {
        return recompense;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public StatutMission getStatut() {
        return statut;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRang(RangMission rang) {
        this.rang = rang;
    }

    public void setGainExp(int gainExp) {
        this.gainExp = gainExp;
    }

    public void setRecompense(int recompense) {
        this.recompense = recompense;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setStatut(StatutMission statut) {
        this.statut = statut;
    }


    @Override
    public String toString() {
        return "Mission [id=" + id + ", nom=" + nom + ", description=" + description + ", rang=" + rang
                + ", gainExp=" + gainExp + ", recompense=" + recompense + ", equipe=" + equipe + ", dateDebut="
                + dateDebut + ", dateFin=" + dateFin + ", statut=" + statut + "]";
    }
    
}