package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.SubjectDao;
import school.entity.Subject;
import school.service.interfaces.SubjectService;

import java.util.List;

/**
 * Created by ArslanovDamir on 26.09.2016.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao;

    @Autowired
    public void setSubjectDao(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }





    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addSubject(Subject subject) {
        this.subjectDao.addSubject(subject);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateSubject(Subject subject) {
        this.subjectDao.updateSubject(subject);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeSubject(int id) {
        this.subjectDao.removeSubject(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Subject getSubjectById(int id) {
        return this.subjectDao.getSubjectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Subject> listSubjects() {
        return this.subjectDao.listSubjects();
    }
}
