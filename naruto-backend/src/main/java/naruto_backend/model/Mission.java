package naruto_backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String titre;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "rang", length = 5,nullable = false, unique = true)
    private RangMission rang;

    @Column(name = "gain_exp", length = 50, nullable = false)
    private int gain_exp;

    @Column(name = "recompense", length = 50, nullable = false)
    private int recompense;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;
    
    @Column(name = "statut", length = 20, nullable = false)
    private EtatMission etat;
    
    
    public Mission() {
    }

    public Mission(Integer id, String titre, String description, RangMission rang, int gain_exp, int recompense,
            Equipe equipe, LocalDate dateDebut, LocalDate dateFin, EtatMission etat) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.rang = rang;
        this.gain_exp = gain_exp;
        this.recompense = recompense;
        this.equipe = equipe;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
    }


    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public RangMission getRang() {
        return rang;
    }

    public int getGain_exp() {
        return gain_exp;
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

    public EtatMission getEtat() {
        return etat;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRang(RangMission rang) {
        this.rang = rang;
    }

    public void setGain_exp(int gain_exp) {
        this.gain_exp = gain_exp;
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

    public void setEtat(EtatMission etat) {
        this.etat = etat;
    }


    @Override
    public String toString() {
        return "Mission [id=" + id + ", titre=" + titre + ", description=" + description + ", rang=" + rang
                + ", gain_exp=" + gain_exp + ", recompense=" + recompense + ", equipe=" + equipe + ", dateDebut="
                + dateDebut + ", dateFin=" + dateFin + ", etat=" + etat + "]";
    }
    
}