package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ArslanovDamir on 15.10.2016.
 */
@Component
@Entity
@Table(name = "parent", catalog = "school")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parentId", unique = true, nullable = false)
    private int parentId;

    @Column(name = "parentName")
    private String parentName;


    @Column(name = "parentSurname")
    private String parentSurname;

    @Column(name = "parenPname")
    private String parenPname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chId")
    private Children children;

    @Column(name = "phoneParent")
    private String parentPhone;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public User() {
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentSurname() {
        return parentSurname;
    }

    public void setParentSurname(String parentSurname) {
        this.parentSurname = parentSurname;
    }

    public String getParenPname() {
        return parenPname;
    }

    public void setParenPname(String parenPname) {
        this.parenPname = parenPname;
    }

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
