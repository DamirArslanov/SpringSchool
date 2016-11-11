package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.SchoolClassDao;
import school.dao.interfaces.TeacherDao;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.security.TRole;
import school.security.TRoleService;
import school.utils.Generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cheshire on 19.09.2016.
 */
@Repository
public class TeacherDaoImpl implements TeacherDao {

    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    SchoolClassDao schoolClassDao;
    @Autowired(required = true)
    public void setSchoolClassDao(SchoolClassDao schoolClassDao) {
        this.schoolClassDao = schoolClassDao;
    }

    private TRoleService tRoleService;
    @Autowired
    public void settRoleService(TRoleService tRoleService) {
        this.tRoleService = tRoleService;
    }

    private Generator generator;
    @Autowired
    private void setGenerator(Generator generator) {
        this.generator = generator;
    }



    public void addTeacher(Teacher teacher) {
        Session session = this.sessionFactory.getCurrentSession();
        Boolean trying = false;

        if (teacher.getUsername().equals("")) {
            while (trying == false) {
                String username = generator.simpleUsernameGenerator("teacher");
                String hql = "FROM Teacher teacher WHERE teacher.username = (:username)";
                Query query = session.createQuery(hql).setParameter("username", username);
                if (query.list().isEmpty()) {
                    teacher.setUsername(username);
                    trying = true;
                }
            }
        }
        if (teacher.getPassword().equals("")) {
            teacher.setPassword(generator.simplePassGenerator());
        }

        session.persist(teacher);
        System.out.println("Teacher добавлен/сохранён. Детали учителя: " + teacher);
    }

    public void updateTeacher(Teacher teacher) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(teacher);
        System.out.println("Teacher обновлен. Детали учителя: " + teacher);
    }

    public void removeTeacher(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Teacher teacher = (Teacher) session.load(Teacher.class, new Integer(id));
        if (teacher!=null) {
            session.delete(teacher);
        }
        System.out.println("Teacher удален. Детали удаленного учителя" + teacher);
    }

    public Teacher getTeacherById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Teacher teacher = (Teacher) session.load(Teacher.class, new Integer(id));
        System.out.println("Teacher загружен по id. Детали учителя" + teacher);
        return teacher;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Teacher> listTeachers() {
        System.out.println("****************ФАБРИКА УЧИТЕЛЕЙ ОТКРЫТА*************************");
        Session session = this.sessionFactory.getCurrentSession();
        List<Teacher> listTeachers = session.createQuery("FROM Teacher").list();
        for (Teacher teacher : listTeachers) {
            System.out.println("TeacherList" + teacher);
        }
        System.out.println("****************ФАБРИКА ЗАКРЫТА*************************");
        return listTeachers;
    }


    //Сюда при редактировании передали id класса,
    // чтобы была возможность добавить его в листУчителей при редактирвоании класса
    //сюда в ID может прийти id класса с НЕЗАПОЛНЕННЫМ УЧИТЕЛЕМ(например, когда-то при создании не указали учителя
    //тогда что делать? Нужно проверить на NULL
    @Override
    public List<Teacher> listEditPlusFreeTeachers(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        //Получили "пустых" учителей
        List<Teacher> listEditPlusFreeTeachers =  session.createQuery("FROM Teacher teacher WHERE teacher.schoolClass is null").list();
        //Теперь нужно добавить в лист редактируемого учителя, класс мы по id вытащим
        SchoolClass schoolClass = (SchoolClass) this.schoolClassDao.getSchoolClassById(id);
        //вытащили учителя
        if (schoolClass.getTeacher() != null) {
            Teacher teacher = (Teacher) getTeacherById(schoolClass.getTeacher().getT_id());
            //добавим его в уже созданный ранее листПустыхУчителей
            listEditPlusFreeTeachers.add(teacher);
        }

        return listEditPlusFreeTeachers;
    }

    //Здесь почти тоже самое что и верхний метод,
    // НО этот просто берет "ПустыхУчителей" и отдает на страницу JSP "Классы" (БЕЗ РЕДАКТИРУЕМОГО УЧИТЕЛЯ)
    @Override
    public List<Teacher> listAllFreeTeachers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Teacher> listAllFreeTeachers =  session.createQuery("FROM Teacher teacher WHERE teacher.schoolClass is null").list();
        return listAllFreeTeachers;
    }


    //Тут ищем ОДИНОЧНОГО ЗАБЫТОГО УЧИТЕЛЯ
    @Override
    public List<Teacher> findByClass(SchoolClass schoolClass) {
        Integer id = null;
        if (schoolClass.getClass_id() != 0) {
            id = (schoolClass.getClass_id());
        }
        Session session = this.sessionFactory.getCurrentSession();
        //Нужно найти учителя у которого в классах прописан определенный класс,
        //класс, который отказался от своего учителя, НО УЧИТЕЛЬ НЕ УЗНАЛ ОБ ЭТОМ
        String hql = "FROM Teacher teacher WHERE teacher.schoolClass.class_id = (:id)";
        Query query = session.createQuery(hql).setParameter("id", id);

        //Пока я не понял, почему возникает ошибка если пытаюсь скастовать
        // одиночного учителя сразу же после его получения из запроса
        List<Teacher> teacherList = query.list();
//        Teacher teacher = (Teacher) teacherList.get(0);
        return teacherList;
    }

    @Override
    public Teacher getTeacherByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Teacher teacher WHERE teacher.username = (:username)";
        Query query = session.createQuery(hql).setParameter("username", username);
        if (!query.list().isEmpty()) {
            Teacher teacher = (Teacher) query.getSingleResult();
            return teacher;
        } else return null;

    }


}
