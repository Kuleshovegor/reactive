package model;

import java.util.Objects;

public class User {
    private final long id;
    private final String login;
    private final Currency currency;

    public User(long id, String login, Currency currency) {
        this.id = id;
        this.login = login;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", currency=" + currency +
                '}';
    }
}
