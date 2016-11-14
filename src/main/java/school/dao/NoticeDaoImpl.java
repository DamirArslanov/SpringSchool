package school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.NoticeDao;
import school.entity.Notice;
import school.entity.SchoolClass;
import school.entity.Teacher;

import java.util.List;

/**
 * Created by ArslanovDamir on 19.10.2016.
 */
@Repository
public class NoticeDaoImpl implements NoticeDao {


    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    @Override
    public void addNotice(Notice notice) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(notice);
    }

    @Override
    public void removeNotice(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Notice notice = (Notice) session.load(Notice.class, new Integer(id));
        if (notice != null) {
            session.delete(notice);
        }
    }

    @Override
    public Notice getNoticeById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Notice notice = (Notice) session.load(Notice.class, new Integer(id));
        return notice;
    }

    @Override
    public List<Notice> getNoticeList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Notice> noticeList = session.createQuery("FROM Notice").list();
        return noticeList;
    }

    @Override
    public List<Notice> getNoticeByClass(SchoolClass schoolClass) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Notice> noticeList = schoolClass.getNotices();
        return noticeList;
    }

    @Override
    public List<Notice> getNotiesByTeacher(Teacher teacher) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Notice> noticeList = teacher.getNotices();
        return noticeList;
    }
}
