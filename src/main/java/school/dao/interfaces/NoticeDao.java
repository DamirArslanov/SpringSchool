package school.dao.interfaces;

import school.entity.Notice;
import school.entity.SchoolClass;
import school.entity.Teacher;


import java.util.List;

/**
 * Created by Cheshire on 19.10.2016.
 */
public interface NoticeDao {

    public void addNotice(Notice notice);

    public void removeNotice(int id);

    public Notice getNoticeById(int id);

    public List<Notice> getNoticeList();

    public List<Notice> getNoticeByClass(SchoolClass schoolClass);

    public List<Notice> getNotiesByTeacher(Teacher teacher);
}
