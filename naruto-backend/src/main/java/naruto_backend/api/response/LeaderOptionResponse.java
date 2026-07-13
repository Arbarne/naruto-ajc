package naruto_backend.api.response;

import naruto_backend.model.Leader;

public record LeaderOptionResponse(Integer id, String nom, String prenom) {

    public static LeaderOptionResponse convert(Leader leader) {
        return new LeaderOptionResponse(leader.getId(), leader.getNom(), leader.getPrenom());
    }

}
