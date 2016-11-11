package school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.LessonTimeDao;
import school.entity.LessonTime;
import school.service.interfaces.LessonTimeService;

import java.util.List;

/**
 * Created by Cheshire on 07.11.2016.
 */
@Service
public class LessonTimeServiceImpl implements LessonTimeService {

    public LessonTimeDao lessonTimeDao;

    @Autowired
    public void setLessonTimeDao(LessonTimeDao lessonTimeDao) {
        this.lessonTimeDao = lessonTimeDao;
    }

    @Override
    @Transactional
    public void addTime(LessonTime lessonTime) {
        this.lessonTimeDao.addTime(lessonTime);
    }

    @Override
    @Transactional
    public void deleteTime(int timeID) {
        this.lessonTimeDao.deleteTime(timeID);
    }

    @Override
    @Transactional
    public LessonTime getTimeByValue(String timeValue) {
        return this.lessonTimeDao.getTimeByValue(timeValue);
    }

    @Override
    @Transactional
    public List<LessonTime> lessonTimeList() {
        return this.lessonTimeDao.lessonTimeList();
    }
}
