package school.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ArslanovDamir on 19.10.2016.
 */
@Component
@Entity
@Table(name = "notice", catalog = "school")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeId", unique = true, nullable = false)
    int noticeID;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Column(name = "noticeDate", nullable = false)
    Date noticeDate;

    @Column(name = "message", nullable = false)
    String message;

    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinTable(name = "noticeClassLink", joinColumns = @JoinColumn(name = "noticeId"), inverseJoinColumns = @JoinColumn(name = "classId"))
    SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinTable(name = "noticeTeacherLink", joinColumns = @JoinColumn(name = "noticeId"), inverseJoinColumns = @JoinColumn(name = "teacherId"))
    Teacher teacher;

    public Notice() {
    }

    public int getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(int noticeID) {
        this.noticeID = noticeID;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
