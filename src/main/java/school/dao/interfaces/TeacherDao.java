package school.dao.interfaces;



import school.entity.SchoolClass;
import school.entity.Teacher;

import java.util.List;

/**
 * Created by ArslanovDamir on 19.09.2016.
 */
public interface TeacherDao {

    public void addTeacher(Teacher teacher);

    public void updateTeacher(Teacher teacher);

    public void removeTeacher(int id);

    public Teacher getTeacherById(int id);

    public List<Teacher> listTeachers();

    public List<Teacher> listEditPlusFreeTeachers(int id);

    public List<Teacher> listAllFreeTeachers();

    public List<Teacher> findByClass(SchoolClass schoolClass);

    public Teacher getTeacherByUsername(String username);

}
