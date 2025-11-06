package account_service.application;

import account_service.domain.UserId;
import common.ddd.Repository;
import common.hexagonal.OutBoundPort;
import account_service.domain.Account;

@OutBoundPort
public interface AccountRepository extends Repository {

    UserId getNextId();

    void addAccount(Account account) throws InvalidAccountIdException, AccountAlreadyPresentException;

    boolean isPresent(UserId userId);

    Account getAccount(UserId userId) throws AccountNotFoundException;

    boolean isValid(UserId userId, String password);
}
