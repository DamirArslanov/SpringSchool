package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cheshire on 19.09.2016.
 */
@Component
@Entity
@Table(name = "schoolClass", catalog = "school")
public class SchoolClass {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classId", unique = true, nullable = false)
    int class_id;

    @Column(name = "className", nullable = false)
    String class_name;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "class")
//    List<Children> scholar = new ArrayList<Children>();

    //@OneToOne(fetch = FetchType.EAGER, mappedBy = "schoolClass", cascade = CascadeType.ALL)



//    @JoinTable(name = "class", joinColumns = @JoinColumn(name = "ID_SCHOOLCLASS"),
//            inverseJoinColumns = @JoinColumn(name = "ID_TEACHER"))
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    Teacher teacher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass")
    List<Children> childrenList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass")
    List<Notice> notices;








    public SchoolClass() {
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Children> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Children> childrenList) {
        this.childrenList = childrenList;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    @Override
    public String toString() {
        return "SchoolClass{" +
                "class_id=" + class_id +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}
