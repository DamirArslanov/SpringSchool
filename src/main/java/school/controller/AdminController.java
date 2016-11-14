package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import school.entity.Children;
import school.entity.LessonTime;
import school.entity.Teacher;
import school.service.interfaces.LessonTimeService;
import school.service.interfaces.TeacherService;
import school.service.interfaces.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by ArslanovDamir on 10.10.2016.
 */
@Controller
public class AdminController {

    private TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    private LessonTimeService lessonTimeService;
    @Autowired
    public void setLessonTimeService(LessonTimeService lessonTimeService) {
        this.lessonTimeService = lessonTimeService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Неправильные данные для входа. Пожалуйста, попробуйте снова.");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы!");
        }
        return "login";
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String getMyFirstPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(username.substring(0,4).equals("user")) {
            return "redirect:/childreninfo/";
        } else return "redirect:/work/workpage";
    }


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "index";
    }


    @RequestMapping(value = "/work/workpage", method = RequestMethod.GET)
    public String getWorkPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (this.teacherService.getTeacherByUsername(username).getSchoolClass() != null) {
            model.addAttribute("classCheck", true);
        }
        return "workpage";
    }

    @RequestMapping(value = "/admin/lessontime", method = RequestMethod.GET)
    public String getLessonsTime(Model model) {
        List<LessonTime> lessonTimeList = this.lessonTimeService.lessonTimeList();
        //сортировка по часам и минутам :[
        if(!lessonTimeList.isEmpty()) {
                Collections.sort(lessonTimeList, new Comparator() {

                    public int compare(Object o1, Object o2) {

                        int i = ((LessonTime) o1).getTime().indexOf(":");
                        Integer str1 = Integer.valueOf(((LessonTime) o1).getTime().substring(0, i));

                        int k = ((LessonTime) o2).getTime().indexOf(":");
                        Integer str2 = Integer.valueOf(((LessonTime) o2).getTime().substring(0, k));

                        int sComp = str1.compareTo(str2);


                        if (sComp != 0) {
                            return  sComp;
                        } else {
                            Integer x1;
                            Integer x2;

                            String sub3 = (((LessonTime) o1).getTime().substring(i+1, ((LessonTime) o1).getTime().length()));
                            if ("00".equals(sub3)) {
                                x1 = 0;
                            } else {
                                x1 = Integer.parseInt(sub3);
                            }

                            String sub4 = (((LessonTime) o2).getTime().substring(k+1, ((LessonTime) o2).getTime().length()));
                            if ("00".equals(sub4)) {
                                x2 = 0;
                            } else {x2 = Integer.parseInt(sub4);}


                            return x1.compareTo(x2);
                        }
                    }});
        }

        model.addAttribute("lessonTime", new LessonTime());
        model.addAttribute("lessonTimeList",lessonTimeList);
        return "lessontime";
    }

    @RequestMapping(value = "/admin/lessontime/add", method = RequestMethod.POST)
    public String addLessonsTime(@ModelAttribute("lessonTime")LessonTime lessonTime) {
        this.lessonTimeService.addTime(lessonTime);
        return "redirect:/admin/lessontime";
    }

    @RequestMapping(value = "/admin/lessontime/{ltimeID}", method = RequestMethod.GET)
    public String deleteLessonsTime(@PathVariable("ltimeID") int ltimeID) {
        this.lessonTimeService.deleteTime(ltimeID);
        return "redirect:/admin/lessontime";
    }



}
