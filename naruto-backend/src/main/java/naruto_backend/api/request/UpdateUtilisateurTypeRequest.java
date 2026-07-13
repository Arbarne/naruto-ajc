package naruto_backend.api.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateUtilisateurTypeRequest {

    @NotBlank
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
