package school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.NoticeDao;
import school.entity.Notice;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.service.interfaces.NoticeService;

import java.util.List;

/**
 * Created by Cheshire on 19.10.2016.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    private NoticeDao noticeDao;

    @Autowired
    public void setNoticeDao(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void addNotice(Notice notice) {
        this.noticeDao.addNotice(notice);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public void removeNotice(int id) {
        this.noticeDao.removeNotice(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public Notice getNoticeById(int id) {
        return this.noticeDao.getNoticeById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Notice> getNoticeList() {
        return this.noticeDao.getNoticeList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_USER')")
    public List<Notice> getNoticeByClass(SchoolClass schoolClass) {
        return this.noticeDao.getNoticeByClass(schoolClass);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    public List<Notice> getNotiesByTeacher(Teacher teacher) {
        return this.noticeDao.getNotiesByTeacher(teacher);
    }
}
