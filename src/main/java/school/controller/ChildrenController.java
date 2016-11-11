package school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import school.entity.Children;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.entity.User;
import school.service.interfaces.ChildrenService;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;
import school.service.interfaces.UserService;


import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.CardChannel;
import javax.validation.Valid;
import javax.ws.rs.Path;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cheshire on 23.09.2016.
 */
@Controller
public class ChildrenController {


    //Нельзя добавлять здесь учителя напрямую. Иначе можно прописать левого учителя != учителю класса.
    // Лучше взять id учителя (имя? + ссылка на неё ${} ) из класса и просто вывести его в доп. столбце. Верно?

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


    private ChildrenService childrenService;

    @Autowired(required = true)
    public void setChildrenService(ChildrenService childrenService) {
        this.childrenService = childrenService;
    }


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @ModelAttribute("schoolClasses")
//    public List<SchoolClass> getAllClasses(){
//        return schoolClassService.listSchoolClass();
//    }
//
//
//    @ModelAttribute("childrens")
//    public List<Children> getAllChildrens() {
//        return childrenService.listChildrens();
//    }
//


    @RequestMapping(value = "/work/parents/{classID}", method = RequestMethod.GET)
    public ModelAndView getPDF(@PathVariable("classID") int classID) {

        Set<User> users = new LinkedHashSet<>();
        List<Children> childrens = this.schoolClassService.getSchoolClassById(classID).getChildrenList();
        Collections.sort(childrens, (o1, o2) -> o1.getCh_surname().compareTo(o2.getCh_surname()));

        if (childrens.get(0).getParent() != null) {
            for (Children children : childrens) {
                if (children.getParent() != null) {
                    users.add(children.getParent());
                }
            }
        } else {
            User defaultParent = new User();
            defaultParent.setParentName("Родители отсутствуют");
            users.add(defaultParent);
        }

        return new ModelAndView("PDFCreator", "users", users);
    }


    @RequestMapping(value = "admin/childrens", method = RequestMethod.GET)
    public String listChildrens(Model model) {
        model.addAttribute("children", new Children());
        model.addAttribute("schoolClasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("listChildrens", this.childrenService.listChildrens());

        return "childrens";
    }

    @RequestMapping(value = "/childreninfo/{childrenID}", method = RequestMethod.GET)
    public String getChildrenInfo(@PathVariable("childrenID") int childrenID, Model model) {
        model.addAttribute("children", this.childrenService.getChildrenById(childrenID));
        return "childreninfo";
    }

    @RequestMapping(value = "/childreninfo/", method = RequestMethod.GET)
    public String getChildrenInfoByParent(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Children children = this.userService.getUserByUsername(username).getChildren();
        Teacher teacher;
        if (children.getTeacher() != null) {
            teacher = children.getTeacher();
            model.addAttribute("teacherID", teacher.getT_id());
            System.out.println("****************************************");
        }
        model.addAttribute("childrenID", children.getCh_id());
        return "redirect:/childreninfo/" + children.getCh_id();
    }

    @RequestMapping(value = "/childreninfo/parent/{parentID}", method = RequestMethod.GET)
    public String getParentInfo(@PathVariable("parentID") int parentID, Model model) {
        model.addAttribute("user", this.userService.getUserById(parentID));
        return "parentinfo";
    }

    @RequestMapping(value = "/childreninfo/parent", method = RequestMethod.POST)
    public String saveParentInfo(@ModelAttribute("user") User user, Model model) {
        this.userService.updateUser(user);
        Children children = this.childrenService.getChildrenById(user.getChildren().getCh_id());
        model.addAttribute("childrenID", children.getCh_id());
        return "redirect:/childreninfo/" + children.getCh_id();
    }


    @RequestMapping(value = "/work/classfill/{classID}", method = RequestMethod.GET)
    public String getClassChildren(@PathVariable("classID") int classID, Model model) {
        Children children = new Children();
        children.setSchoolClass(this.schoolClassService.getSchoolClassById(classID));
        model.addAttribute("schoolClasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("children", children);
        model.addAttribute("listChildrens", this.schoolClassService.getSchoolClassById(classID).getChildrenList());
        return "classfill";
    }

    @RequestMapping(value = "/work/classfill/{classID}/get/{childID}", method = RequestMethod.GET)
    public String getChildrenInsideClassfill(@PathVariable("classID") int classID,
                                             @PathVariable("childID") int childID,
                                             Model model) {
        model.addAttribute("children", this.childrenService.getChildrenById(childID));
        model.addAttribute("schoolClasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("listChildrens", this.schoolClassService.getSchoolClassById(classID).getChildrenList());
        return "classfill";
    }


    @RequestMapping(value = "/work/classfill/add", method = RequestMethod.POST)
    public String addChildrenInsideClassfill(@ModelAttribute("children") Children children, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "childrens";
        }
        if (children.getCh_id() == 0) {
            this.childrenService.addChildren(children);
        }else {
            this.childrenService.updateChildren(children);
        }
        return "redirect:/work/classfill/" + children.getSchoolClass().getClass_id();
    }

    @RequestMapping(value = "/work/classfill/{classID}/del/{childID}", method = RequestMethod.GET)
    public String removeChildrenInsideClassfill(@PathVariable("classID") int classID,
                                                @PathVariable("childID") int childID,
                                                Model model) {
        this.childrenService.removeChildren(childID);
        model.addAttribute("listChildrens", this.schoolClassService.getSchoolClassById(classID).getChildrenList());
        return "redirect:/work/classfill/" + classID;
    }



    @RequestMapping(value = "admin/childrens/add", method = RequestMethod.POST)
    public String addChildren(@Valid @ModelAttribute("children") Children children, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "childrens";
        }

        if (children.getCh_id() == 0) {
            this.childrenService.addChildren(children);
        }else {
            this.childrenService.updateChildren(children);
        }
        return "redirect:/admin/childrens";
    }

    @RequestMapping(value = "/work/classinfo/addchild", method = RequestMethod.POST)
    public String addChildrenFromClass(@ModelAttribute ("children") Children children) {
        if (children.getCh_id() == 0) {
            this.childrenService.addChildren(children);
        }else {
            this.childrenService.updateChildren(children);
        }
        return "redirect:/classinfo";
    }


    @RequestMapping("admin/childrens/edit/{id}")
    public String updateChildren(@PathVariable("id") int id, Model model) {
        model.addAttribute("children", this.childrenService.getChildrenById(id));
        model.addAttribute("schoolClasses", this.schoolClassService.listSchoolClass());
        model.addAttribute("listChildrens", this.childrenService.listChildrens());
        return "childrens";
    }

    @RequestMapping("admin/childrens/delete/{id}")
    public String removeChildren(@PathVariable("id") int id) {
        this.childrenService.removeChildren(id);
        return "redirect:/admin/childrens";
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        binder.registerCustomEditor(SchoolClass.class, "schoolClass", new PropertyEditorSupport() {
            public void setAsText(String text) {
                if (!("null".equals(text))) {
                    Integer class_id = Integer.parseInt(text);
                    SchoolClass schoolClass = (SchoolClass) schoolClassService.getSchoolClassById(class_id);
                    setValue(schoolClass);
                }
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




    }
//    тут мы должны получить "текст даты рождения" и преобразовать его в нужный формат
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

}
