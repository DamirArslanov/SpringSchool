package school.dao.interfaces;




import school.entity.Weekday;

import java.util.List;

/**
 * Created by Cheshire on 27.09.2016.
 */
public interface WeekdayDao {

    public Weekday getWeekdayById(int id);

    public List<Weekday> listWeekdays();

}
