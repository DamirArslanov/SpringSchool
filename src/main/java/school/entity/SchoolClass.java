package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ArslanovDamir on 19.09.2016.
 */
@Component
@Entity
@Table(name = "schoolClass", catalog = "school")
public class SchoolClass {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classId", unique = true, nullable = false)
    private int class_id;

    @Column(name = "className", nullable = false)
    private  String class_name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass")
    private List<Children> childrenList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass")
    private List<Notice> notices;

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
