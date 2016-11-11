package school.entity;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Cheshire on 12.10.2016.
 */
@Component
public class ScheduleForm {

    public ScheduleForm() {
    }

    private Teacher teacher;

    private SchoolClass schoolClass;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
}
