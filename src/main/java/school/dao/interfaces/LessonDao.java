package school.dao.interfaces;



import school.entity.Lesson;
import school.entity.Schedule;
import school.entity.Teacher;

import java.util.Date;
import java.util.List;

/**
 * Created by ArslanovDamir on 01.10.2016.
 */
public interface LessonDao {

    public void addLesson(Lesson lesson);

    public void updateLesson(Lesson lesson);

    public void removeLesson(int id);

    public Lesson getLessonById(int id);

    public List<Lesson> listLessons();

    public void createLessonBySchedule(Schedule schedule);

    public List<Lesson> getLessonsByTeacher(String teacherUsername);

    public void createWeekLessons();

    public void createWeekLessonsByClass(int classID, boolean future);

    public List<Lesson> getSelectedMonthLessons(Teacher teacher, Date startDate, Date endDate);

}
