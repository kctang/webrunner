package model.demo.jpa;

import net.big2.webrunner.core.jpa.crud.BaseEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity {
    @NotEmpty
    @Size(min = 3, max = 50)
    private String username;
    @Size(min = 1, max = 250)
    private String firstName;
    @Size(min = 1, max = 250)
    private String lastName;
    @Email
    private String email;
    @NotEmpty
    @Size(max = 100)
    private String password;

    @ManyToMany
    @JoinTable(name = "User_Group")
    private Set<Group> groups;

    public User() {
        groups = new HashSet<Group>();
    }

    public User(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        groups = new HashSet<Group>();
    }

    public void addGroup(Group group) {
        if (group != null) {
            groups.add(group);
            if (!group.getUsers().contains(this)) {
                group.addUser(this);
            }
        }
    }

    public void deleteGroup(Group group) {
        if (group != null) {
            group.getUsers().remove(this);
            groups.remove(group);
        }
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        // clear current groups
        for (Group group : this.groups) {
            group.getUsers().remove(this);
        }
        this.groups.clear();

        // add new groups
        if (groups != null) {
            for (Group group : groups) {
                addGroup(group);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getDisplayName() {
        return getUsername();
    }
}
