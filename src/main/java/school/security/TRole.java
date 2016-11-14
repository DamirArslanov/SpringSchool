package school.security;


import org.springframework.stereotype.Component;
import school.entity.Teacher;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by ArslanovDamir on 09.10.2016.
 */
@Component
@Entity
@Table(name = "trole", catalog = "school")
public class TRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRId", unique = true, nullable = false)
    private int TRId;

    @Column(name = "role")
    private String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "TRId"),
            inverseJoinColumns = @JoinColumn(name = "teacherId"))
    private List<Teacher> teachers;

    public TRole() {
    }

    public int getTRId() {
        return TRId;
    }

    public void setTRId(int TRId) {
        this.TRId = TRId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "TRole{" +
                "TRId=" + TRId +
                ", role='" + role + '}';
    }
}
