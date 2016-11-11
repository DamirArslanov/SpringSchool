package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by Cheshire on 07.11.2016.
 */
@Component
@Entity
@Table(name = "lessonTime", catalog = "school")
public class LessonTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeID", unique = true, nullable = false)
    int timeID;

    @Column(name = "time", nullable = false)
    String time;

    public LessonTime() {
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LessonTime{" +
                "time='" + time + '\'' +
                '}';
    }
}
