package lobby_service.infrastructure;

import common.hexagonal.Adapter;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lobby_service.application.UserSession;
import lobby_service.application.UserSessionNotFoundException;
import lobby_service.application.UserSessionRepository;
import lobby_service.domain.UserId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * 
 * A simple file-based implementation of the UserSessionRepository.
 * 
 */
@Adapter
public class SimpleFileBasedUserSessionRepository implements UserSessionRepository {
	static Logger logger = Logger.getLogger("[UserSessionDB]");

	private static final String USER_SESSION_PREFIX = "user-session-";

	/* db file */
	static final String DB_USER_SESSION = "user_sessions.json";

	private final HashMap<String, UserSession> userSessions;
	private int sessionCount;

	public SimpleFileBasedUserSessionRepository() {
		this.userSessions = new HashMap<>();
		this.sessionCount = 0;
		initFromDB();
	}

	@Override
	public String addSession(final UserId userId) {
		final String sessionId = USER_SESSION_PREFIX + this.sessionCount++;
		this.userSessions.put(sessionId, new UserSession(sessionId, userId));
		saveOnDB();
		return sessionId;
	}

	@Override
	public boolean isPresent(String userSessionId) {
		return this.userSessions.containsKey(userSessionId);
	}

	@Override
	public UserSession getSession(String userSessionId) throws UserSessionNotFoundException {
		if (!this.isPresent(userSessionId)) {
			throw new UserSessionNotFoundException();
		}
		return this.userSessions.get(userSessionId);
	}

	private void initFromDB() {
		try {
			var userSessionsDB = new BufferedReader(new FileReader(DB_USER_SESSION));
			var sb = new StringBuilder();
			while (userSessionsDB.ready()) {
				sb.append(userSessionsDB.readLine()).append("\n");
			}
			userSessionsDB.close();
			var array = new JsonArray(sb.toString());
			for (int i = 0; i < array.size(); i++) {
				var session = array.getJsonObject(i);
				final UserSession userSession = new UserSession(session.getString("userSessionId"),
						new UserId(session.getString("userId")));
				this.userSessions.put(userSession.sessionId(), userSession);
			}
		} catch (Exception ex) {
			logger.info("DB not found, creating an empty one.");
			saveOnDB();
		}
	}
	
	private void saveOnDB() {
		try {
			JsonArray list = new JsonArray();
			for (final UserSession userSession: this.userSessions.values()) {
				var obj = new JsonObject();
				obj.put("userSessionId", userSession.sessionId());
				obj.put("userId", userSession.userId().id());
				list.add(obj);
			}
			var userSessionsDB = new FileWriter(DB_USER_SESSION);
			userSessionsDB.append(list.encodePrettily());
			userSessionsDB.flush();
			userSessionsDB.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
}
