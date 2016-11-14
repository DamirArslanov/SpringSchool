package school.service.interfaces;



import school.entity.Children;
import school.entity.Lesson;
import school.entity.Rating;
import school.entity.Subject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Cheshire on 28.09.2016.
 */
public interface RatingService {

    public void addRating(Rating rating);

    public void updateRating(Rating rating);

    public void removeRating(int id);

    public Rating getRatingById(int id);

    public List<Rating> getRatingsByChildren(int childrenID, Date date);

    public List<Rating> listRatings();

    public List<Rating> getRatingsByLesson(int lesson_id);

    public void addRatingWithMap(Map<Integer, String> ratingMap, Lesson lesson);

    public List<Rating> getMonthRating(Date startDate, Date endDate, Children children, Subject subject);
}
