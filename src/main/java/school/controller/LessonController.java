package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import school.entity.Form.LessonSearchForm;
import school.entity.Lesson;
import school.entity.Teacher;
import school.service.interfaces.LessonService;
import school.service.interfaces.RatingService;
import school.service.interfaces.TeacherService;
import school.utils.DateUtil;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Cheshire on 01.10.2016.
 */
@Controller
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    private RatingService ratingService;

    @Autowired
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private TeacherService teacherService;

    @Autowired(required = true)
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ModelAttribute("teachers")
    public List<Teacher> getAllTeachers() {
        return  this.teacherService.listTeachers();
    }

    private DateUtil dateUtil;

    @Autowired
    public void setDateUtil(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }



//    @PostAuthorize("hasRole('ROLE_ADMIN') or isAuthenticated() and principal.getUsername() == returnObject.schedule.teacher.username")
    @RequestMapping(value = "/lesson/update/{less_id}", method = RequestMethod.GET)
    public String updateLesson(@PathVariable("less_id") int less_id, Model model) {

        Lesson lesson = this.lessonService.getLessonById(less_id);
        model.addAttribute("less_id", less_id);
        model.addAttribute("lesson", lesson);
        return "urok";
    }

    @RequestMapping(value = "/lesson/update/{less_id}", method = RequestMethod.POST)
    public String updateLessonPost(@PathVariable("less_id") int less_id,
                                   @ModelAttribute("lesson") Lesson lesson, Model model) {

        if(lesson.getRatingMap() != null) {
            Map<Integer, String> map = lesson.getRatingMap();
            this.ratingService.addRatingWithMap(map, lesson);
        }

        if (lesson.getLess_id() == 0) {
            this.lessonService.addLesson(lesson);
        }else {
            this.lessonService.updateLesson(lesson);
        }


        int id = lesson.getLess_id();
        model.addAttribute("less_id", less_id);
        model.addAttribute("lesson", lesson);
        return "redirect:/lesson/update/" + id;
    }






    @RequestMapping("/lesson/{less_id}")
    public String getLessonById(@PathVariable("less_id") int less_id, Model model) {
        model.addAttribute("lesson", this.lessonService.getLessonById(less_id));
        return "lesson";
    }



    @RequestMapping("lesson/create")
    public void createLesson(Model model) {
        this.lessonService.createWeekLessons();
//        model.addAttribute("listLessons" , this.lessonService.listLessons());
//        return "lesson";
    }

    @RequestMapping("timetable/update/{classID}")
    public String updateLessonsByClass(@PathVariable("classID") int classID) {
        this.lessonService.createWeekLessonsByClass(classID, false);
        return "redirect:/work/timetable/" + classID;
    }

    @RequestMapping("timetable/create/{classID}")
    public String createFutureLessonsByClass(@PathVariable("classID") int classID) {
        this.lessonService.createWeekLessonsByClass(classID, true);
        return "redirect:/work/timetable/" + classID;
    }




    @RequestMapping(value = "/work/tlessons", method = RequestMethod.POST)
    public String getSelectedLessons(@ModelAttribute("LessonSearchForm")LessonSearchForm lessonSearchForm,
                                     Model model) {
        List<Lesson> lessons = new ArrayList<>();

        lessons = this.lessonService.getSelectedMonthLessons(lessonSearchForm.getTeacher(),
                                                             lessonSearchForm.getStartDate(),
                                                             lessonSearchForm.getEndDate());
        model.addAttribute("lessons", lessons);
        System.out.println("******************************");
        System.out.println("******************************");
        System.out.println("******************************");
        System.out.println(lessonSearchForm.getTeacher().getT_id());
    if (lessonSearchForm.getTeacher() != null) {
        model.addAttribute("teacher", this.teacherService.getTeacherById(lessonSearchForm.getTeacher().getT_id()));
    }
        LessonSearchForm lessonSearchForm1 = new LessonSearchForm();
        lessonSearchForm1.setStartDate(lessonSearchForm.getStartDate());
        lessonSearchForm1.setEndDate(lessonSearchForm.getEndDate());
        lessonSearchForm1.setTeacher(lessonSearchForm.getTeacher());
        model.addAttribute("LessonSearchForm",lessonSearchForm1);
        return "tlessons";
    }


    @RequestMapping("/work/tlessons")
    public String getLessonsByTeacher(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Teacher teacher = this.teacherService.getTeacherByUsername(username);
        model.addAttribute("teacher", teacher);
        if(teacher.getSchoolClass() != null) {
            model.addAttribute("classCheck", true);
        }
        LessonSearchForm lessonSearchForm = new LessonSearchForm();
        lessonSearchForm.setStartDate(this.dateUtil.getFirstDayMonth());
        lessonSearchForm.setEndDate(this.dateUtil.getLastDayMonth());
        model.addAttribute("lessons", this.lessonService.getLessonsByTeacher(username));
        model.addAttribute("LessonSearchForm", lessonSearchForm);
        model.addAttribute("username", username);
        return "tlessons";
    }


//    @RequestMapping("/schoolclasses/schedule/{id}")
//    public String findScheduleClass(@PathVariable("id") int id, Model model) {
//        model.addAttribute("schedule", new Schedule());
//        model.addAttribute("listsSchedules", this.scheduleService.listScheduleClass(id));
//        return "schedule";
//    }


    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Teacher.class, "teacher", new PropertyEditorSupport() {
            public void setAsText(String text) {
                if (!("null".equals(text))) {
                    Integer t_id = Integer.parseInt(text);
                    Teacher teacher = (Teacher) teacherService.getTeacherById(t_id);
                    setValue(teacher);
                } else setValue(null);

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

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }



}


