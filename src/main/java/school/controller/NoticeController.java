package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import school.entity.Notice;
import school.entity.Teacher;
import school.service.interfaces.NoticeService;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ArslanovDamir on 19.10.2016.
 */
@Controller
public class NoticeController {

    private NoticeService noticeService;
    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    private SchoolClassService schoolClassService;
    @Autowired
    public void setSchoolClassService(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    private TeacherService teacherService;
    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "/work/notice/add/{classID}", method = RequestMethod.GET)
    public String addNoticeGet(@PathVariable("classID") int classID, Model model) {
        model.addAttribute("schoolClass", this.schoolClassService.getSchoolClassById(classID));
        Notice notice = new Notice();
        notice.setNoticeDate(new Date());
        model.addAttribute("notice", notice);
        return "addnotice";
    }

    @RequestMapping(value = "/work/class/notice/delete/{noticeID}", method = RequestMethod.GET)
    public String deleteNotice(@PathVariable("noticeID") int noticeID, Model model) {
       this.noticeService.removeNotice(noticeID);
        return "redirect:/work/classinfo";
    }

    @RequestMapping(value = "/work/notice/add/{classID}", method = RequestMethod.POST)
    public String addNoticePost(@ModelAttribute("notice") Notice notice,
                                @PathVariable("classID") int classID) {
        notice.setSchoolClass(this.schoolClassService.getSchoolClassById(classID));
        this.noticeService.addNotice(notice);
        return "redirect:/work/classinfo";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        binder.registerCustomEditor(Teacher.class, "teacher", new PropertyEditorSupport() {
            public void setAsText(String text) {
                if (!("null".equals(text))) {
                    Integer t_id = Integer.parseInt(text);
                    Teacher teacher = (Teacher) teacherService.getTeacherById(t_id);
                    setValue(teacher);
                }
            }

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Teacher teacher = (Teacher) value;
                    return teacher.getT_name();
                }
                return null;
            }
        });

    }
}
