package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.SchoolClassDao;
import school.dao.interfaces.TeacherDao;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.security.TRole;
import school.security.TRoleDao;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;
import school.utils.Generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Cheshire on 19.09.2016.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private Generator generator;
    @Autowired
    private void setGenerator(Generator generator) {
        this.generator = generator;
    }

    private TRoleDao tRoleDao;

    @Autowired
    public void settRoleDao(TRoleDao tRoleDao) {
        this.tRoleDao = tRoleDao;
    }

    SchoolClassDao schoolClassDao;
    @Autowired(required = true)
    public void setSchoolClassDao(SchoolClassDao schoolClassDao) {
        this.schoolClassDao = schoolClassDao;
    }


    private SchoolClassService schoolClassService;

    @Autowired(required = true)
    public void setSchoolClassService(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    private TeacherDao teacherDao;

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }




    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addTeacher(Teacher teacher) {

//        List<TRole> roles = new ArrayList<>();
//        roles.add(this.tRoleDao.getTRoleByName("ROLE_TEACHER"));
//        teacher.setRoles(roles);
//        System.out.println("************ФАБРИКА УЧИТЕЛЬСКИХ РОЛЕЙ ГОВОРИТ!***************");
//        System.out.println(teacher.getRoles());
//        System.out.println("************ФАБРИКА УЧИТЕЛЬСКИХ РОЛЕЙ НЕГОВОРИТ!***************");

//        if (teacher.getSchoolClass() != null) {
//            SchoolClass schoolClass = (SchoolClass) schoolClassService.getSchoolClassById(teacher.getSchoolClass().getClass_id());
//            schoolClass.setTeacher(teacher);
//            schoolClassService.updateSchoolClass(schoolClass);
//        }
        this.teacherDao.addTeacher(teacher);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateTeacher(Teacher teacher) {

//        if (teacher.getSchoolClass() != null) {
//            SchoolClass schoolClass = (SchoolClass) schoolClassService.getSchoolClassById(teacher.getSchoolClass().getClass_id());
//            schoolClass.setTeacher(teacher);
//            schoolClassService.updateSchoolClass(schoolClass);
//        }
        this.teacherDao.updateTeacher(teacher);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeTeacher(int id) {
        this.teacherDao.removeTeacher(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_USER')")
    public Teacher getTeacherById(int id) {
        return this.teacherDao.getTeacherById(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Teacher> listTeachers() {
        return this.teacherDao.listTeachers();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Teacher> listEditPlusFreeTeachers(int id) {
        return this.teacherDao.listEditPlusFreeTeachers(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Teacher> listAllFreeTeachers() {
        return this.teacherDao.listAllFreeTeachers();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Teacher> findByClass(SchoolClass schoolClass) {
        return this.teacherDao.findByClass(schoolClass);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public Teacher getTeacherByUsername(String username) {
        return this.teacherDao.getTeacherByUsername(username);
    }
}
