package naruto_backend.api.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class StartMissionRequest {

    @NotNull
    private LocalDate dateDebut;

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    
}
