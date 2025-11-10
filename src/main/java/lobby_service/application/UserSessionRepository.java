package lobby_service.application;

import common.ddd.Repository;
import common.hexagonal.OutBoundPort;
import lobby_service.domain.UserId;

@OutBoundPort
public interface UserSessionRepository extends Repository {

    String addSession(UserId userId);

    boolean isPresent(String userSessionId);

    UserSession getSession(String userSessionId) throws UserSessionNotFoundException;
}
