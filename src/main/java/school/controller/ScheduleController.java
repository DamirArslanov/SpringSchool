package school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import school.entity.*;
import school.service.interfaces.*;


import java.beans.PropertyEditorSupport;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Cheshire on 26.09.2016.
 */
@Controller
public class ScheduleController {

    private WeekdayService weekdayService;

    @Autowired(required = true)
    public void setWeekdayService(WeekdayService weekdayService) {
        this.weekdayService = weekdayService;
    }

    private ScheduleService scheduleService;

    @Autowired(required = true)
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private SubjectService subjectService;

    @Autowired(required = true)
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
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

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    private LessonTimeService lessonTimeService;

    @Autowired
    public void setLessonTimeService(LessonTimeService lessonTimeService) {
        this.lessonTimeService = lessonTimeService;
    }

    //    @ModelAttribute("schoolclasses")
//    public List<SchoolClass> getAllClasses(){
//        return schoolClassService.listSchoolClass();
//    }
//
//    @ModelAttribute("teachers")
//    public List<Teacher> getAllTeachers() {
//        return  this.teacherService.listTeachers();
//    }
//
//
//    @ModelAttribute("subjects")
//    public List<Subject> getAllSubjects() {
//        return subjectService.listSubjects();
//    }
//
//
//    @ModelAttribute("weekdays")
//    public List<Weekday> getAllWeekdays(){
//        return weekdayService.listWeekdays();
//    }


//    @ModelAttribute("schedules")
//    public List<Schedule> getAllSchedules() {
//        return  this.scheduleService.listSchedules();
//    }


    @RequestMapping(value = "admin/schedules", method = RequestMethod.GET)
    public String listsSchedules(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getPrincipal().equals("anonymousUser")) {
            Teacher teacher = this.teacherService.getTeacherByUsername(auth.getName());
            if (teacher.getSchoolClass() != null) {
                model.addAttribute("classCheck", true);
            }
        }
        List<Teacher> teachers = this.teacherService.listTeachers();
        List<SchoolClass> schoolClasses = this.schoolClassService.listSchoolClass();
        List<Schedule> schedules = this.scheduleService.listScheduleClass(schoolClasses.get(0).getClass_id());

        ScheduleForm scheduleForm = new ScheduleForm();
        if (!schoolClasses.isEmpty()) {
            scheduleForm.setSchoolClass(schoolClasses.get(0));
        }


        model.addAttribute("schoolclasses", schoolClasses);
        model.addAttribute("teachers", teachers);
        model.addAttribute("subjects", this.subjectService.listSubjects());
        model.addAttribute("weekdays", this.weekdayService.listWeekdays());
        model.addAttribute("lessonTimeList", this.lessonTimeService.lessonTimeList());
        model.addAttribute("ScheduleForm", scheduleForm);
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("schedules", schedules);

        return "schedules";
    }


    @RequestMapping(value = "/work/timetable/{classID}", method = RequestMethod.GET)
    public String getClassTimetable(@PathVariable("classID") int classID, Model model) {

        model.addAttribute("schoolclasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("teachers", this.teacherService.listTeachers());
        model.addAttribute("subjects", this.subjectService.listSubjects());
        model.addAttribute("weekdays", this.weekdayService.listWeekdays());
        model.addAttribute("lessonTimeList", this.lessonTimeService.lessonTimeList());

        Schedule schedule = new Schedule();
        schedule.setSchoolClass(this.schoolClassService.getSchoolClassById(classID));
        model.addAttribute("schedule", schedule);

        List<Schedule> schedules = this.scheduleService.listScheduleClass(classID);
        if (!schedules.isEmpty()) {
            compareObjects(schedules);
        }
        model.addAttribute("schedules", schedules);
        return "classtimetable";
    }

    @RequestMapping(value = "/childreninfo/timetable/{classID}", method = RequestMethod.GET)
    public String getChildTimetable(@PathVariable("classID") int classID, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(username.substring(0,4).equals("user")) {
            Children children = this.userService.getUserByUsername(username).getChildren();
            model.addAttribute("children", children);
        }
        List<Schedule> schedules = this.scheduleService.listScheduleClass(classID);
        if (!schedules.isEmpty()) {
            compareObjects(schedules);
        }
        model.addAttribute("schedules", schedules);
        return "classtimetable";
    }

    @RequestMapping(value = "/work/timetable/add", method = RequestMethod.POST)
    public String addScheduleInTimetable(@ModelAttribute("schedule") Schedule schedule, BindingResult result) {

        if (schedule.getShed_id() == 0) {
            this.scheduleService.addSchedule(schedule);
        }else {
            this.scheduleService.updateSchedule(schedule);
        }
        return "redirect:/work/timetable/" + schedule.getSchoolClass().getClass_id();
    }

    @RequestMapping("/work/timetable/edit/{id}")
    public String getScheduleInTimetable(@PathVariable("id") int id, Model model) {
        model.addAttribute("lessonTimeList", this.lessonTimeService.lessonTimeList());
        model.addAttribute("schoolclasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("teachers", this.teacherService.listTeachers());
        model.addAttribute("subjects", this.subjectService.listSubjects());
        model.addAttribute("weekdays", this.weekdayService.listWeekdays());

        Schedule schedule = (Schedule) this.scheduleService.getScheduleById(id);
        model.addAttribute("schedule", schedule);

        List<Schedule> schedules = this.scheduleService.listScheduleClass(schedule.getSchoolClass().getClass_id());
        if (!schedules.isEmpty()) {
            compareObjects(schedules);
        }
        model.addAttribute("schedules", schedules);
        return "classtimetable";
    }

    @RequestMapping("/work/timetable/delete/{id}")
    public String removeScheduleInTimetable(@PathVariable("id") int id) {
        int classID = this.scheduleService.getScheduleById(id).getSchoolClass().getClass_id();
        this.scheduleService.removeSchedule(id);
        return "redirect:/work/timetable/" + classID;
    }




    @RequestMapping(value = "/admin/schedules", method = RequestMethod.POST)
    public String giveSelectedSchedules(@ModelAttribute("ScheduleForm") ScheduleForm scheduleForm, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getPrincipal().equals("anonymousUser")) {
            Teacher teacher = this.teacherService.getTeacherByUsername(auth.getName());
            if (teacher.getSchoolClass() != null) {
                model.addAttribute("classCheck", true);
            }
        }
        model.addAttribute("lessonTimeList", this.lessonTimeService.lessonTimeList());
        model.addAttribute("schoolclasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("teachers", this.teacherService.listTeachers());
        model.addAttribute("subjects", this.subjectService.listSubjects());
        model.addAttribute("weekdays", this.weekdayService.listWeekdays());
        List<Schedule> schedules;

        if (scheduleForm.getSchoolClass() == null & scheduleForm.getTeacher() != null) {
            //...000? ну... наверное, такой индекс в базе никогда не назначат...может быть.
            //так вообще пишут? Перегрузка?
            schedules = this.scheduleService.listSelectedShedule(000, scheduleForm.getTeacher().getT_id());
            if (!schedules.isEmpty()) {
                compareObjects(schedules);
            }
            model.addAttribute("schedules", schedules);
        }else if (scheduleForm.getTeacher() == null & scheduleForm.getSchoolClass() != null) {
            schedules = this.scheduleService.listSelectedShedule(scheduleForm.getSchoolClass().getClass_id(), 000);
            if (!schedules.isEmpty()) {
                compareObjects(schedules);
            }
            model.addAttribute("schedules", schedules);
        }else if (scheduleForm.getTeacher() == null & scheduleForm.getSchoolClass() == null){
            schedules = this.scheduleService.listSchedules();
            if (!schedules.isEmpty()) {
                compareObjects(schedules);
            }
            model.addAttribute("schedules", schedules);
        }else {
            schedules = this.scheduleService.listSelectedShedule(scheduleForm.getSchoolClass().getClass_id(), scheduleForm.getTeacher().getT_id());
            if (!schedules.isEmpty()) {
                compareObjects(schedules);
            }
            model.addAttribute("schedules", schedules);
        }
        System.out.println("******************НАС ВЫЗВАЛИ!**************************");
        model.addAttribute("ScheduleForm", scheduleForm);
        model.addAttribute("schedule", new Schedule());

        return "schedules";
    }



    @RequestMapping("admin/teachers/schedule/{id}")
    public String findScheduleTeacher(@PathVariable("id") int id, Model model) {
        model.addAttribute("schedule", new Schedule());

        List<Schedule> schedules = this.scheduleService.listScheduleTeacher(id);
        if (!schedules.isEmpty()) {
            compareObjects(schedules);
        }
        model.addAttribute("schedules", schedules);
        return "tschedule";
    }





    @RequestMapping(value = "admin/schedules/add", method = RequestMethod.POST)
    public String addSchedule(@ModelAttribute("schedule") Schedule schedule, BindingResult result) {


        if (schedule.getShed_id() == 0) {
            this.scheduleService.addSchedule(schedule);
        }else {
            this.scheduleService.updateSchedule(schedule);
        }
        return "redirect:/admin/schedules";
    }


    @RequestMapping("admin/schedules/edit/{id}")
    public String updateSchedule(@PathVariable("id") int id, Model model) {
        model.addAttribute("lessonTimeList", this.lessonTimeService.lessonTimeList());
        model.addAttribute("schoolclasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("teachers", this.teacherService.listTeachers());
        model.addAttribute("subjects", this.subjectService.listSubjects());
        model.addAttribute("weekdays", this.weekdayService.listWeekdays());

        Schedule schedule = (Schedule) this.scheduleService.getScheduleById(id);
        model.addAttribute("schedule", schedule);

        List<Schedule> schedules = this.scheduleService.listScheduleClass(schedule.getSchoolClass().getClass_id());
        if (!schedules.isEmpty()) {
            compareObjects(schedules);
        }
        model.addAttribute("schedules", schedules);
        model.addAttribute("ScheduleForm", new ScheduleForm());
        return "schedules";
    }

    @RequestMapping("admin/schedules/delete/{id}")
    public String removeSchedule(@PathVariable("id") int id) {
        this.scheduleService.removeSchedule(id);
        return "redirect:/admin/schedules";
    }



    public void compareObjects(List<Schedule> schedules) {
        Collections.sort(schedules, new Comparator() {

            public int compare(Object o1, Object o2) {

                int i = ((Schedule) o1).getTime().indexOf(":");
                Integer str1 = Integer.valueOf(((Schedule) o1).getTime().substring(0, i));

                int k = ((Schedule) o2).getTime().indexOf(":");
                Integer str2 = Integer.valueOf(((Schedule) o2).getTime().substring(0, k));

                int sComp = str1.compareTo(str2);


                if (sComp != 0) {
                    return  sComp;
                } else {
                    Integer x1;
                    Integer x2;

                    String sub3 = (((Schedule) o1).getTime().substring(i+1, ((Schedule) o1).getTime().length()));
                    if ("00".equals(sub3)) {
                        x1 = 0;
                    } else {
                        x1 = Integer.parseInt(sub3);
                    }

                    String sub4 = (((Schedule) o2).getTime().substring(k+1, ((Schedule) o2).getTime().length()));
                    if ("00".equals(sub4)) {
                        x2 = 0;
                    } else {x2 = Integer.parseInt(sub4);}


                    return x1.compareTo(x2);
                }
            }});
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        binder.registerCustomEditor(SchoolClass.class, "schoolClass", new PropertyEditorSupport() {
            public void setAsText(String text) {
                if (!("null".equals(text))) {
                Integer class_id = Integer.parseInt(text);
                SchoolClass schoolClass = (SchoolClass) schoolClassService.getSchoolClassById(class_id);
                setValue(schoolClass);
                } else setValue(null);
            }

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    SchoolClass schoolClass = (SchoolClass) value;

                    return schoolClass.getClass_name();
                }
                return null;
            }
        });

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

        binder.registerCustomEditor(Subject.class, "subject", new PropertyEditorSupport() {
            public void setAsText(String text) {
                Integer sub_id = Integer.parseInt(text);
                Subject subject = (Subject) subjectService.getSubjectById(sub_id) ;
                setValue(subject);

            }

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Subject subject = (Subject) value;

                    return subject.getSub_name();
                }
                return null;
            }
        });

        binder.registerCustomEditor(Weekday.class, "weekday", new PropertyEditorSupport() {
            public void setAsText(String text) {
                Integer week_id = Integer.parseInt(text);
                Weekday weekday = (Weekday) weekdayService.getWeekdayById(week_id);
                setValue(weekday);

            }

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Weekday weekday = (Weekday) value;

                    return weekday.getWeek_name();
                }
                return null;
            }
        });




    }






















}
