package school.entity;

import org.springframework.stereotype.Component;
import school.security.TRole;


import javax.persistence.*;
import java.util.List;


/**
 * Created by Cheshire on 10.10.2016.
 */
@Component
@Entity
@Table(name = "teacher", catalog = "school")
public class Teacher {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacherId", unique = true, nullable = false)
    private int t_id;

    @Column(name = "teacherName", nullable = false)
    private String t_name;

    @Column(name = "teacherSurname", nullable = false)
    private String t_surname;

    @Column(name = "teacherPname", nullable = false)
    private String t_pname;

    @Column(name = "teacherPhone")
    private String teacherPhone;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "teacherId"),
            inverseJoinColumns = @JoinColumn(name = "TRId"))
    private List<TRole> roles;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId")
    SchoolClass schoolClass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    List<Notice> notices;


    @Transient
    private String FIO;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgID")
    private Image image;


    public Teacher() {
    }




    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_surname() {
        return t_surname;
    }

    public void setT_surname(String t_surname) {
        this.t_surname = t_surname;
    }

    public String getT_pname() {
        return t_pname;
    }

    public void setT_pname(String t_pname) {
        this.t_pname = t_pname;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TRole> roles) {
        this.roles = roles;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

        public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private String getFIOT(String t_surname, String t_name, String t_pname) {
        return t_surname + " " + t_name.substring(0, 1).toUpperCase() + ". " + t_pname.substring(0, 1).toUpperCase() + ".";
    }

    public String getFIO() {
        return getFIOT(getT_surname(),getT_name(), getT_pname());
    }

//    public void setFIO(String FIO) {
//        this.FIO = FIO;
//    }

    @Override
    public String toString() {
        return "Teacher{" +
                "t_id=" + t_id +
                ", t_name='" + t_name + '\'' +
                ", t_surname='" + t_surname + '\'' +
                ", t_pname='" + t_pname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles.getClass() +
                '}';
    }
}
