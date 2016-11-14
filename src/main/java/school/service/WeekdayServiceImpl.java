package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.WeekdayDao;
import school.entity.Weekday;
import school.service.interfaces.WeekdayService;

import java.util.List;

/**
 * Created by ArslanovDamir on 27.09.2016.
 */
@Service
public class WeekdayServiceImpl implements WeekdayService {

    WeekdayDao weekdayDao;

    @Autowired(required = true)
    public void setWeekdayDao(WeekdayDao weekdayDao) {
        this.weekdayDao = weekdayDao;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public Weekday getWeekdayById(int id) {
        return this.weekdayDao.getWeekdayById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Weekday> listWeekdays() {
        return this.weekdayDao.listWeekdays();
    }
}
