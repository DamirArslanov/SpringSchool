package school.service.interfaces;



import school.entity.SchoolClass;

import java.util.List;

/**
 * Created by Cheshire on 19.09.2016.
 */
public interface SchoolClassService {

    public void addSchoolClass(SchoolClass schoolClass);

    public void updateSchoolClass(SchoolClass schoolClass);

    public void removeSchoolClass(int id);

    public SchoolClass getSchoolClassById(int id);

    public List<SchoolClass> listSchoolClass();

}
