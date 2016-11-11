package school.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Cheshire on 26.09.2016.
 */


@Component
@Entity
@Table(name = "lesson", catalog = "school")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonId", unique = true, nullable = false)
    int less_id;

    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern="dd.MM.yyyy HH:mm:ss")
    @Column(name = "lessonDate", nullable = false)
    Date less_Date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    Schedule schedule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lesson")
    List<Rating> ratingList;

    @Column(name = "description")
    String description;

    @Transient
    private Map<Integer, String> ratingMap;


    public Lesson() {
    }

    public int getLess_id() {
        return less_id;
    }

    public void setLess_id(int less_id) {
        this.less_id = less_id;
    }


    public Date getLess_Date() {
        return less_Date;
    }

    public void setLess_Date(Date less_Date) {
        this.less_Date = less_Date;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Integer, String> getRatingMap() {
        return ratingMap;
    }

    public void setRatingMap(Map<Integer, String> ratingMap) {
        this.ratingMap = ratingMap;
    }
}
