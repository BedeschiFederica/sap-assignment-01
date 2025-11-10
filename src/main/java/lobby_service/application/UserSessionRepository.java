package lobby_service.application;

import common.ddd.Repository;
import common.hexagonal.OutBoundPort;

@OutBoundPort
public interface UserSessionRepository extends Repository {

    void addSession(UserSession userSession);

    boolean isPresent(String userSessionId);

    UserSession getSession(String userSessionId) throws UserSessionNotFoundException;
}
