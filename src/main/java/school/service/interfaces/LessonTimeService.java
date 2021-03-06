package school.service.interfaces;

import school.entity.LessonTime;

import java.util.List;

/**
 * Created by ArslanovDamir on 07.11.2016.
 */
public interface LessonTimeService {

    public void addTime (LessonTime lessonTime);

    public void deleteTime(int timeID);

    public LessonTime getTimeByValue(String timeValue);

    public List<LessonTime> lessonTimeList();
}
