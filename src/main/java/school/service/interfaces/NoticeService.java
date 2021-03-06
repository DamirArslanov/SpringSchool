package school.service.interfaces;

import school.entity.Notice;
import school.entity.SchoolClass;
import school.entity.Teacher;

import java.util.List;

/**
 * Created by ArslanovDamir on 19.10.2016.
 */
public interface NoticeService {

    public void addNotice(Notice notice);

    public void removeNotice(int id);

    public Notice getNoticeById(int id);

    public List<Notice> getNoticeList();

    public List<Notice> getNoticeByClass(SchoolClass schoolClass);

    public List<Notice> getNotiesByTeacher(Teacher teacher);
}
