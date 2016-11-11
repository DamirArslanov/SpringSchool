package school.entity.Form;

import school.entity.Children;
import school.entity.Subject;

import java.util.Date;

/**
 * Created by Cheshire on 17.10.2016.
 */
public class RatingForm {

    public RatingForm() {
    }

    private Date startDate;

    private Date endDate;

    private Children children;

    private Subject subject;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
