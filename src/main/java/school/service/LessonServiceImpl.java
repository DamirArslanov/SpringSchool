package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.LessonDao;
import school.entity.Lesson;
import school.entity.Schedule;
import school.entity.Teacher;
import school.service.interfaces.LessonService;

import java.util.Date;
import java.util.List;

/**
 * Created by Cheshire on 01.10.2016.
 */
@Service
public class LessonServiceImpl implements LessonService {

    LessonDao lessonDao;

    @Autowired
    public void setLessonDao(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    @Override
    @Transactional
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public void addLesson(Lesson lesson) {
        this.lessonDao.addLesson(lesson);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void updateLesson(@P("lesson") Lesson lesson) {
        this.lessonDao.updateLesson(lesson);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeLesson(int id) {
        this.lessonDao.removeLesson(id);
    }

    @Override
    @Transactional
    @PostAuthorize("hasRole('ROLE_ADMIN') or isAuthenticated() and principal.getUsername() == returnObject.schedule.teacher.username")
    public Lesson getLessonById(int id) {
        return this.lessonDao.getLessonById(id);
    }


    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Lesson> listLessons() {
        return this.lessonDao.listLessons();
    }

//    @Override
//    @Transactional
//    public void createLessonBySchedule(Schedule schedule) {
//        this.lessonDao.createLessonBySchedule(schedule);
//    }


    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Lesson> getLessonsByTeacher(String teacherUsername) {
        return this.lessonDao.getLessonsByTeacher(teacherUsername);
    }

    //    @Scheduled(fixedRate=10000)
    @Override
    @Transactional
//    @Scheduled(cron="0 14 20 ? * 2")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createWeekLessons() {
        this.lessonDao.createWeekLessons();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void createWeekLessonsByClass(int classID, boolean future) {
        this.lessonDao.createWeekLessonsByClass(classID, future);
    }


    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Lesson> getSelectedMonthLessons(Teacher teacher, Date startDate, Date endDate) {
        return this.lessonDao.getSelectedMonthLessons(teacher, startDate, endDate);
    }
}
