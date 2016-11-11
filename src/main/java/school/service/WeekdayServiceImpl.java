package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.WeekdayDao;
import school.entity.Weekday;
import school.service.interfaces.WeekdayService;

import java.util.List;

/**
 * Created by Cheshire on 27.09.2016.
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
    public Weekday getWeekdayById(int id) {
        return this.weekdayDao.getWeekdayById(id);
    }

    @Override
    @Transactional
    public List<Weekday> listWeekdays() {
        return this.weekdayDao.listWeekdays();
    }
}
