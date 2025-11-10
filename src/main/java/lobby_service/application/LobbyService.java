package lobby_service.application;

import common.hexagonal.InBoundPort;
import lobby_service.domain.DeliveryId;

/**
 * 
 * Interface of the Lobby Service at the application layer
 * 
 */
@InBoundPort
public interface LobbyService {

	String login(String userName, String password) throws LoginFailedException;
	
	DeliveryId createNewDelivery(String userSessionId) throws CreateDeliveryFailedException;
	
	String trackDelivery(String userSessionId, DeliveryId deliveryId) throws TrackDeliveryFailedException;
    
}
