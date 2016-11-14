package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.ScheduleDao;
import school.entity.Schedule;
import school.service.interfaces.ScheduleService;
import school.service.interfaces.UserService;

import java.util.List;

/**
 * Created by ArslanovDamir on 26.09.2016.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleDao scheduleDao;

    @Autowired
    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


    public UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addSchedule(Schedule schedule) {
        this.scheduleDao.addSchedule(schedule);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateSchedule(Schedule schedule) {
        this.scheduleDao.updateSchedule(schedule);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeSchedule(int id) {
        this.scheduleDao.removeSchedule(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Schedule getScheduleById(int id) {
        return this.scheduleDao.getScheduleById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Schedule> listSchedules() {
        return this.scheduleDao.listSchedules();
    }


    /*
    * @PostAuthorize так должен работать?
    */

    @Override
    @Transactional
    @PostAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN') or (isAuthenticated() AND !returnObject.isEmpty() AND returnObject != null AND this.userService.getUserByUsername(principal.getUsername()).children.schoolClass.class_id == returnObject.get(0).schoolClass.class_id)")
//    @PostAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN') or (#returnObject!=null and hasRole('ROLE_USER'))")
    public List<Schedule> listScheduleClass(int id) throws IllegalArgumentException {
        return this.scheduleDao.listScheduleClass(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Schedule> listScheduleTeacher(int id) {
        return this.scheduleDao.listScheduleTeacher(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Schedule> listSelectedShedule(int schedule, int teacher) {
        return this.scheduleDao.listSelectedShedule(schedule, teacher);
    }
}
