package school.service.interfaces;



import school.entity.Weekday;

import java.util.List;

/**
 * Created by ArslanovDamir on 27.09.2016.
 */
public interface WeekdayService {
    public Weekday getWeekdayById(int id);

    public List<Weekday> listWeekdays();
}
