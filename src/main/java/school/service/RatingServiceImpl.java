package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.RatingDao;
import school.entity.Children;
import school.entity.Lesson;
import school.entity.Rating;
import school.entity.Subject;
import school.service.interfaces.RatingService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Cheshire on 28.09.2016.
 */
@Service
public class RatingServiceImpl implements RatingService {

    private RatingDao ratingDao;

    @Autowired
    public void setRatingDao(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }


    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void addRating(Rating rating) {
        this.ratingDao.addRating(rating);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void updateRating(Rating rating) {
        this.ratingDao.updateRating(rating);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void removeRating(int id) {
        this.ratingDao.removeRating(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public Rating getRatingById(int id) {
        return this.ratingDao.getRatingById(id);
    }

    @Override
    @Transactional
//    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PostAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN') or isAuthenticated() and principal.getUsername() == returnObject.get(0).children.parent.username")
    public List<Rating> getRatingsByChildren(int childrenID) {
        return this.ratingDao.getRatingsByChildren(childrenID);
    }


    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Rating> listRatings() {
        return this.ratingDao.listRatings();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Rating> getRatingsByLesson(int lesson_id) {
        return this.ratingDao.getRatingsByLesson(lesson_id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void addRatingWithMap(Map<Integer, String> ratingMap, Lesson lesson) {
        this.ratingDao.addRatingWithMap(ratingMap, lesson);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_USER')")
    public List<Rating> getMonthRating(Date startDate, Date endDate, Children children, Subject subject) {
        return this.ratingDao.getMonthRating(startDate, endDate, children, subject);
    }
}
