package lobby_service.application;

import common.ddd.ValueObject;
import lobby_service.domain.DeliveryId;

import java.util.Objects;

/**
 * 
 * Representing a tracking session, created when a user logs in.
 * 
 */
public record TrackingSession(String sessionId, String userSessionId, DeliveryId deliveryId) implements ValueObject {

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		TrackingSession that = (TrackingSession) object;
		return Objects.equals(sessionId, that.sessionId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(sessionId);
	}
}
