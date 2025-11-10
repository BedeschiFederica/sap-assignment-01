package lobby_service.application;

import common.hexagonal.OutBoundPort;
import lobby_service.domain.*;

@OutBoundPort
public interface DeliveryService {

	DeliveryId createNewDelivery() throws CreateDeliveryFailedException, ServiceNotAvailableException;
	
	String trackDelivery(DeliveryId deliveryId) throws TrackDeliveryFailedException, ServiceNotAvailableException;
	    
}
