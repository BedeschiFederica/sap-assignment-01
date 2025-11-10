package lobby_service.application;

import common.ddd.ValueObject;
import lobby_service.domain.UserId;

import java.util.Objects;

/**
 * 
 * Representing a user session, created when a user logs in.
 * 
 */
public record UserSession(String sessionId, UserId userId) implements ValueObject {


	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		UserSession that = (UserSession) object;
		return Objects.equals(sessionId, that.sessionId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(sessionId);
	}
}
