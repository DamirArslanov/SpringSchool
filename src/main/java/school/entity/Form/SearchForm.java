package school.entity.Form;

import school.entity.Teacher;

import java.util.Date;

/**
 * Created by Cheshire on 17.10.2016.
 */
public class SearchForm {

    public SearchForm() {
    }

    private String month;

    private String year;

    private Date startDate;

    private Date endDate;

    private Teacher teacher;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}