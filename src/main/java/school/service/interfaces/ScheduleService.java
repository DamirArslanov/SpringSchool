package school.service.interfaces;


import school.entity.Schedule;

import java.util.List;

/**
 * Created by ArslanovDamir on 26.09.2016.
 */
public interface ScheduleService {

    public void addSchedule(Schedule schedule);

    public void updateSchedule(Schedule schedule);

    public void removeSchedule(int id);

    public Schedule getScheduleById(int id);

    public List<Schedule> listSchedules();

    public List<Schedule> listScheduleClass(int id);

    public List<Schedule> listScheduleTeacher(int id);

    public List<Schedule> listSelectedShedule(int schedule, int teacher);
}
