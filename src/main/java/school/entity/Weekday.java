package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by ArslanovDamir on 27.09.2016.
 *
 */
@Component
@Entity
@Table(name = "weekday", catalog = "school")
public class Weekday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekdayId", unique = true, nullable = false)
    private int week_id;

    @Column(name = "weekdayName", nullable = false)
    @NotNull
    private String week_name;

    public Weekday() {
    }

    public String getWeek_name() {
        return week_name;
    }

    public void setWeek_name(String week_name) {
        this.week_name = week_name;
    }

    public int getWeek_id() {
        return week_id;
    }

    public void setWeek_id(int week_id) {
        this.week_id = week_id;
    }
}
