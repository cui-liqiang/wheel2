package app.domains;

import com.thoughtworks.orm.annotation.HasMany;
import com.thoughtworks.orm.annotation.PrimaryKey;

import java.util.List;

public class Person {
    private int id;
    private String name;
    private int age;
    private List<Phone> phones;

    @PrimaryKey
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @HasMany
    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
