package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.SchoolClassDao;
import school.dao.interfaces.TeacherDao;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;

import java.util.List;

/**
 * Created by Cheshire on 19.09.2016.
 */
@Service
public class SchoolClassServiceImpl implements SchoolClassService {
    //TeacherDao
    private TeacherDao teacherDao;
    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    //TeacherService
    private TeacherService teacherService;
    @Autowired(required = true)
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    //SchoolClassDao
    SchoolClassDao schoolClassDao;
    @Autowired(required = true)
    public void setSchoolClassDao(SchoolClassDao schoolClassDao) {
        this.schoolClassDao = schoolClassDao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addSchoolClass(SchoolClass schoolClass) {

        //Тут мы прописываем автоматическле заполнение у учителя строки с id'шником класса
        //Логично, что при "прописывании учителя" в классе - учитель "узнает" об этом и пропишет у себя класс
        //Взаимность проще говоря

        //UPDATE: Если отредактировать класс и поставить пустого учителя - учитель не узнает об этом
        //И список "ПустыхУчителей" будет без этого учителя, т.о. можно всех перезаписать
        if (schoolClass.getTeacher() != null) {
            Teacher teacher = (Teacher) teacherService.getTeacherById(schoolClass.getTeacher().getT_id());
            teacher.setSchoolClass(schoolClass);
            teacherService.updateTeacher(teacher);
        }
        this.schoolClassDao.addSchoolClass(schoolClass);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateSchoolClass(SchoolClass schoolClass) {
        //Тоже самое
        if (schoolClass.getTeacher() != null) {
            Teacher teacher = (Teacher) teacherService.getTeacherById(schoolClass.getTeacher().getT_id());
            teacher.setSchoolClass(schoolClass);
            teacherService.updateTeacher(teacher);

            //найти всех ЛЕВЫХ ПОЗАБЫТЫХ учителей
            //и почистить у всех поле класса, кроме того, что добавили сейчас
            List<Teacher> checkList = (List<Teacher>) teacherService.findByClass(schoolClass);

            for (Teacher checkMe : checkList) {
                if (teacher.getT_id() != checkMe.getT_id()){
                    checkMe.setSchoolClass(null);
                    teacherService.updateTeacher(checkMe);
                }
            }


        }else {
            //Нужно найти учителя у которого прописан этот класс и "занулить этот участок"
            List<Teacher> teacherList = (List<Teacher>) teacherService.findByClass(schoolClass);
            for (Teacher check : teacherList) {
                check.setSchoolClass(null);
                teacherService.updateTeacher(check);
            }


        }
        this.schoolClassDao.updateSchoolClass(schoolClass);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeSchoolClass(int id) {
        this.schoolClassDao.removeSchoolClass(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public SchoolClass getSchoolClassById(int id) {
        return this.schoolClassDao.getSchoolClassById(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<SchoolClass> listSchoolClass() {
        return this.schoolClassDao.listSchoolClass();
    }


}
