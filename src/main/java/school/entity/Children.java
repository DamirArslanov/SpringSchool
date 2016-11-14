package school.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by ArslanovDamir on 19.09.2016.
 */
@Component
@Entity
@Table(name = "children", catalog = "school")
public class Children {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chId", unique = true, nullable = false)
    int ch_id;


    @Column(name = "chName", nullable = false)
    @NotNull
    String ch_name;

    @Size(min=1, max=20,
            message="Фамилия должна содержать хотя бы один символ!")
    @Column(name = "chSurname", nullable = false)
    String ch_surname;

    @Column(name = "chPname", nullable = false)
    String ch_Pname;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Column(name = "birthDate", nullable = false)
    Date birthDate;

    @Column(name = "childrenAnddress")
    String address;

    @Column(name = "childrenPhone")
    String phone;



    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinTable(name = "class", joinColumns = @JoinColumn(name = "ch_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
    SchoolClass schoolClass;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    Teacher teacher;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    User parent;

    @Transient
    private String FIO;





    public Children() {
    }

    public int getCh_id() {
        return ch_id;
    }

    public void setCh_id(int ch_id) {
        this.ch_id = ch_id;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getCh_surname() {
        return ch_surname;
    }

    public void setCh_surname(String ch_surname) {
        this.ch_surname = ch_surname;
    }

    public String getCh_Pname() {
        return ch_Pname;
    }

    public void setCh_Pname(String ch_Pname) {
        this.ch_Pname = ch_Pname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public String getFIO() {
        return getFIOChildren(ch_surname, ch_name, ch_Pname);
    }





    private String getFIOChildren(String ch_surname, String ch_name, String ch_Pname) {
        return ch_surname + " " + ch_name.substring(0, 1).toUpperCase() + ". " + ch_Pname.substring(0, 1).toUpperCase() + ".";
    }


    @Override
    public String toString() {
        return "Children{" +
                "ch_Pname='" + ch_Pname + '\'' +
                ", ch_id=" + ch_id +
                ", ch_name='" + ch_name + '\'' +
                ", ch_surname='" + ch_surname + '\'' +
                '}';
    }
}
