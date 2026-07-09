package naruto_backend.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import naruto_backend.api.request.CreateMissionRequest;
import naruto_backend.api.request.UpdateMissionRequest;
import naruto_backend.api.response.EntityCreatedResponse;
import naruto_backend.api.response.EntityUpdatedResponse;
import naruto_backend.api.response.MissionDetailsResponse;
import naruto_backend.api.response.MissionListResponse;
import naruto_backend.service.MissionService;

@RestController
@RequestMapping("/mission")
public class MissionApiController {

    private final MissionService service ;

    public MissionApiController (MissionService service) {
        this.service = service ;
    }

    @GetMapping
    public List<MissionListResponse> findAll() {
        return service.getAll()
                .stream()
                .map(MissionListResponse::convert)
                .toList();
    }

    @GetMapping("/{id}")
    public MissionDetailsResponse findById(@PathVariable Integer id) {
        return MissionDetailsResponse.convert(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse create(@Valid @RequestBody CreateMissionRequest request) {
    return new EntityCreatedResponse(service.insert(request).getId());
}

    @PutMapping("/{id}")
    public EntityUpdatedResponse update(@PathVariable Integer id, @Valid @RequestBody UpdateMissionRequest request) {
    service.update(id, request);

    return new EntityUpdatedResponse(id, true);
}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        service.delete(id);
    }

    

}
