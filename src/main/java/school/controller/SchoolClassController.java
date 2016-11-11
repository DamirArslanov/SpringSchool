package school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import school.entity.Children;
import school.entity.Notice;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.service.interfaces.ChildrenService;
import school.service.interfaces.NoticeService;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Cheshire on 21.09.2016.
 */
@Controller
public class SchoolClassController {

    private NoticeService noticeService;

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    private ChildrenService childrenService;

    @Autowired(required = true)
    public void setChildrenService(ChildrenService childrenService) {
        this.childrenService = childrenService;
    }


    private SchoolClassService schoolClassService;

    @Autowired(required = true)
    public void setSchoolClassService(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }


    private TeacherService teacherService;

    @Autowired(required = true)
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }



    @ModelAttribute("listFTeachers")
    public List<Teacher> getAllClassess(){
        return teacherService.listAllFreeTeachers();
    }



    @ModelAttribute("listschoolClasses")
    public List<SchoolClass> getAllClasses() {
        return schoolClassService.listSchoolClass();
    }


    @ModelAttribute("childrens")
    public List<Children> getAllChildrens() {
        return childrenService.listChildrens();
    }

    @RequestMapping(value = "/work/classinfo/{classID}", method = RequestMethod.GET)
    public String getClassInfoByID(@PathVariable("classID") int classID, Model model) {
        SchoolClass schoolClass = this.schoolClassService.getSchoolClassById(classID);
        model.addAttribute("schoolClass", schoolClass);
        Notice notice = new Notice();
        notice.setNoticeDate(new Date());
        model.addAttribute("notice", notice);
        Children children = new Children();
        children.setSchoolClass(schoolClass);
        return "classinfo";
    }



    @RequestMapping(value = "/work/classinfo", method = RequestMethod.GET)
    public String getClassInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Teacher teacher = this.teacherService.getTeacherByUsername(username);
        if (teacher.getClass() != null) {
            model.addAttribute("schoolClass", teacher.getSchoolClass());
        } else {
            model.addAttribute("message", "Данный учитель не является классным руководителем.");
        }
        Notice notice = new Notice();
        notice.setNoticeDate(new Date());
        model.addAttribute("notice", notice);
        Children children = new Children();
        children.setSchoolClass(teacher.getSchoolClass());
        return "classinfo";
    }


    @RequestMapping(value = "admin/schoolclasses", method = RequestMethod.GET)
    public String listschoolClasses(Model model) {
        model.addAttribute("schoolClass", new SchoolClass());
        model.addAttribute("listschoolClasses", this.schoolClassService.listSchoolClass());

        return "schoolclasses";
    }

    @RequestMapping(value = "admin/schoolclasses/add", method = RequestMethod.POST)
    public String addSchoolClass(@ModelAttribute("schoolClass") SchoolClass schoolClass) {
        if (schoolClass.getClass_id() == 0) {
            this.schoolClassService.addSchoolClass(schoolClass);
        } else {
            this.schoolClassService.updateSchoolClass(schoolClass);
        }
        return "redirect:/admin/schoolclasses";
    }

    @RequestMapping("admin/schoolclasses/delete/{id}")
    public String removeSchoolClasses(@PathVariable("id") int id) {
        this.schoolClassService.removeSchoolClass(id);
        return "redirect:/admin/schoolclasses";
    }


    @RequestMapping("admin/schoolclasses/edit/{id}")
    public String updateSchoolClass(@PathVariable("id") int id, Model model) {
        model.addAttribute("schoolClass", this.schoolClassService.getSchoolClassById(id));

        if (id != 0) {
            model.addAttribute("listFTeachers", this.teacherService.listEditPlusFreeTeachers(id));
        }
        model.addAttribute("listschoolClasses", this.schoolClassService.listSchoolClass());
        return "schoolclasses";
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
