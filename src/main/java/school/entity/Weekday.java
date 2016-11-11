package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Cheshire on 27.09.2016.
 *
 * Будем считать этот класс временным решением проблемы с учебными днями.
 * Возможно, что позже придет иное решение.
 */
@Component
@Entity
@Table(name = "weekday", catalog = "school")
public class Weekday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekdayId", unique = true, nullable = false)
    int week_id;

    @Column(name = "weekdayName", nullable = false)
    @NotNull
    String week_name;

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
