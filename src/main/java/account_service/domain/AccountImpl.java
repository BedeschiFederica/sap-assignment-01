package account_service.domain;

public class AccountImpl implements Account {

    private final String userName;
    private final String password;
    private final long whenCreated;

    public AccountImpl(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
        this.whenCreated = System.currentTimeMillis();
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public long getWhenCreated() {
        return this.whenCreated;
    }

    @Override
    public UserId getId() {
        return new UserId(this.userName);
    }
}
