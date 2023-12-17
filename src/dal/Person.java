package dal;

public class Person {
    private final String name;
    private final String password;
    public Person(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public int getPasswordHash() {
        return this.password.hashCode();
    }

}
