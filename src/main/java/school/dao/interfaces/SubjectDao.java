package school.dao.interfaces;



import school.entity.Subject;

import java.util.List;

/**
 * Created by Cheshire on 26.09.2016.
 */
public interface SubjectDao {

    public void addSubject(Subject subject);

    public void updateSubject(Subject subject);

    public void removeSubject(int id);

    public Subject getSubjectById(int id);

    public List<Subject> listSubjects();


//    public List<Subject> findByClass(SchoolClass schoolClass);
}
