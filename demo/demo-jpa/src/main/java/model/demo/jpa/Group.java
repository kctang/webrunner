package model.demo.jpa;

import net.big2.webrunner.core.jpa.crud.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"Group\"")
public class Group extends BaseEntity {
    @NotEmpty
    @Size(min = 3, max = 250)
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    public Group() {
        this.users = new HashSet<User>();
    }

    public void addUser(User user) {
        if (user != null) {
            users.add(user);
            if (!user.getGroups().contains(this)) {
                user.addGroup(this);
            }
        }
    }

    public void deleteUser(User user) {
        if (user != null) {
            user.getGroups().remove(this);
            users.remove(user);
        }
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        // clear current users
        for (User user : this.users) {
            user.getGroups().remove(this);
        }
        this.users.clear();

        // add new users
        if (users != null) {
            for (User user : users) {
                addUser(user);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
