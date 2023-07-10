package de.comparus.opensource.longmap;


import java.util.Objects;

public class UserMock {
    private Long id;
    private String name;

    public UserMock(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMock userMock = (UserMock) o;
        return Objects.equals(getId(), userMock.getId()) && Objects.equals(getName(), userMock.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
