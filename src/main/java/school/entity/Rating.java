package school.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ArslanovDamir on 28.09.2016.
 */
@Component
@Entity
@Table(name = "rating", catalog = "school")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ratingId", unique = true, nullable = false)
    int rt_id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Column(name = "ratingDate", nullable = false)
    Date rt_Date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chId", nullable = false)
    Children children;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId", nullable = false)
    Subject subject;

    @Column(name = "evaluation", nullable = false)
    int evaluation;

    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinTable(name = "lessonRatings", joinColumns = @JoinColumn(name = "ratingId"), inverseJoinColumns = @JoinColumn(name = "lessonId"))
    Lesson lesson;


    public Rating() {
    }


    public int getRt_id() {
        return rt_id;
    }

    public void setRt_id(int rt_id) {
        this.rt_id = rt_id;
    }

    public Date getRt_Date() {
        return rt_Date;
    }

    public void setRt_Date(Date rt_Date) {
        this.rt_Date = rt_Date;
    }

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

}
