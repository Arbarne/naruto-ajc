package naruto_backend.api.request;

import jakarta.validation.constraints.NotNull;

public class AssignMissionRequest {

    @NotNull
    private Integer id;

    @NotNull
    private Integer equipeId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Integer equipeId) {
        this.equipeId = equipeId;
    }  

}
