package lobby_service.domain;

public class UserImpl implements User {

    private final String userName;
    private final String password;

    public UserImpl(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
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
    public UserId getId() {
        return new UserId(this.userName);
    }
}
