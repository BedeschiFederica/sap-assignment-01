package lobby_service.application;

import lobby_service.domain.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * Implementation of the Service entry point at the application layer
 * 
 */
public class LobbyServiceImpl implements LobbyService {
	static Logger logger = Logger.getLogger("[Lobby Service]");
    
    private UserSessionRepository userSessionRepository;
    private int sessionCount;
    private AccountService accountService;
    private DeliveryService deliveryService;
    
    public LobbyServiceImpl(){
    	this.userSessionRepository = null;
    	sessionCount = 0;
    }
    
    @Override
	public String login(final String userId, final String password) throws LoginFailedException {
		logger.log(Level.INFO, "Login: " + userId + " " + password);
		try {
			final UserId id = new UserId(userId);
			if (!this.accountService.isValidPassword(id, password)) {
				throw new LoginFailedException();
			}
			this.sessionCount++;
			final String sessionId = "user-session-" + this.sessionCount;
			final UserSession userSession = new UserSession(sessionId, id);
			this.userSessionRepository.addSession(userSession);
			return userSession.sessionId();
		} catch (final UserNotFoundException | ServiceNotAvailableException  ex) {
			throw new LoginFailedException();
		}
	}

	@Override
	public DeliveryId createNewDelivery(final String userSessionId) throws CreateDeliveryFailedException {
		try {
			if (this.userSessionRepository.isPresent(userSessionId)) {
				final DeliveryId deliveryId = this.deliveryService.createNewDelivery();
				logger.log(Level.INFO, "create new delivery " + userSessionId + " " + deliveryId);
				return deliveryId;
			} else {
				throw new CreateDeliveryFailedException();
			}
		} catch (final ServiceNotAvailableException ex) {
			throw new CreateDeliveryFailedException();
		}
	}

	@Override
	public String trackDelivery(final String userSessionId, final DeliveryId deliveryId) throws TrackDeliveryFailedException {
		logger.log(Level.INFO, "Track delivery " + userSessionId + " " + deliveryId);
		try {
			if (this.userSessionRepository.isPresent(userSessionId)) {
				return this.deliveryService.trackDelivery(deliveryId);
			} else {
				throw new TrackDeliveryFailedException();
			}
		} catch (final ServiceNotAvailableException ex) {
			throw new TrackDeliveryFailedException();
		}
	}
	    
	public void bindAccountService(final AccountService service) {
		this.accountService = service;
	}

	public void bindDeliveryService(final DeliveryService service) {
		this.deliveryService = service;
	}

}
