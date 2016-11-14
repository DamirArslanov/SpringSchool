package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by Cheshire on 25.09.2016.
 */
@Component
@Entity
@Table(name = "schedule", catalog = "school")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleId", unique = true, nullable = false)
    int shed_id;


    @Column(name = "time", nullable = false)
    String time;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekdayId", nullable = false)
    Weekday weekday;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId", nullable = false)
    Subject subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId", nullable = false)
    SchoolClass schoolClass;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId", nullable = false)
    Teacher teacher;

    public Schedule() {
    }

    public int getShed_id() {
        return shed_id;
    }

    public void setShed_id(int shed_id) {
        this.shed_id = shed_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
