package dal;

public class Account {
    private final String name;
    private final String password;
    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public int getPasswordHash() {
        return this.password.hashCode();
    }

}
