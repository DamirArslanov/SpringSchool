package school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import school.entity.Children;
import school.entity.Image;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.security.TRole;
import school.security.TRoleService;
//import school.security.teacherSecurity.SchoolSecurityService;
import school.security.teacherSecurity.SchoolSecurityService;
import school.service.interfaces.ImageService;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;
import school.service.interfaces.UserService;


import java.beans.PropertyEditorSupport;
import java.util.*;

/**
 * Created by ArslanovDamir on 19.09.2016.
 */
@Controller
public class TeacherController {

    CommonsMultipartResolver multipartResolver;
    @Autowired
    public void setCommonsMultipartResolver(CommonsMultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }

    ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    private TRoleService tRoleService;
    @Autowired
    public void settRoleService(TRoleService tRoleService) {
        this.tRoleService = tRoleService;
    }

    private SchoolSecurityService schoolSecurityService;
    @Autowired
    public void setSchoolSecurityService(SchoolSecurityService schoolSecurityService) {
        this.schoolSecurityService = schoolSecurityService;
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


    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/admin/teachers/PDF", method = RequestMethod.GET)
    public ModelAndView getPDF() {
        return new ModelAndView("PDFCreator", "teachers", this.teacherService.listTeachers());
    }


    @RequestMapping(value = "admin/teachers", method = RequestMethod.GET)
    public String listTeachers(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("listTeachers", this.teacherService.listTeachers());
        return "teachers";
    }

    @RequestMapping(value = "teacherinfo/{teacherID}", method = RequestMethod.GET)
    public String getTeacherInfo(@PathVariable("teacherID") int teacherID, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(username.substring(0,4).equals("user")) {
            Children children = this.userService.getUserByUsername(username).getChildren();
            model.addAttribute("childrenID", children.getCh_id());
        }
        model.addAttribute("teacher", this.teacherService.getTeacherById(teacherID));
        return "teacherinfo";
    }

    @RequestMapping(value = "admin/teachers/add", method = RequestMethod.POST)
    public String addTeacher(@ModelAttribute("teacher") Teacher teacher,
                             @RequestParam(value = "file", required=false) MultipartFile file,
                             @RequestParam(value = "imageID", required=false) Integer id) {

        if (!file.isEmpty()) {
            Image image = (Image) this.imageService.addImage(file);
            teacher.setImage(image);
        }
        if (file.isEmpty() & id != null) {
            teacher.setImage(this.imageService.getImageById(id));
        }

        if (teacher.getT_id() == 0) {
            this.teacherService.addTeacher(teacher);
        }else {
            this.teacherService.updateTeacher(teacher);
        }
        return "redirect:/admin/teachers";
    }

    @ModelAttribute("roles")
    public List<TRole> getAllProjects() {
        return tRoleService.T_ROLES_LIST();
    }


    @RequestMapping("admin/teachers/delete/{id}")
    public String removeTeacher(@PathVariable("id") int id) {
        this.teacherService.removeTeacher(id);
        return "redirect:/admin/teachers";
    }


    @RequestMapping("admin/teachers/edit/{id}")
    public String updateTeacher(@PathVariable("id") int id, Model model) {
        Teacher teacher = (Teacher) this.teacherService.getTeacherById(id);
        //ВРЕМЕННЫЙ И ВЫНУЖДЕННЫЙ КОД = PersistentBag
        Teacher p = new Teacher();
        p.setImage(teacher.getImage());
        p.setT_id(teacher.getT_id());
        p.setT_name(teacher.getT_name());
        p.setT_surname(teacher.getT_surname());
        p.setT_pname(teacher.getT_pname());
        if (teacher.getTeacherPhone() != null) {
            p.setTeacherPhone(teacher.getTeacherPhone());
        } else p.setTeacherPhone(null);
        p.setUsername(teacher.getUsername());
        p.setPassword(teacher.getPassword());
        p.setSchoolClass(teacher.getSchoolClass());
        List<TRole> roles = new ArrayList<>();
        List<TRole> temp = teacher.getRoles();
        for (TRole role : temp) {
            roles.add(tRoleService.getTRoleById(role.getTRId()));
        }
        p.setRoles(roles);
        //ВРЕМЕННЫЙ И ВЫНУЖДЕННЫЙ КОД = PersistentBag
        if (teacher.getImage() != null) {
            model.addAttribute("imageID", teacher.getImage().getId());
        }
        model.addAttribute("teacher", p);
        model.addAttribute("listTeachers", this.teacherService.listTeachers());
        return "teachers";
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        binder.registerCustomEditor(SchoolClass.class, "schoolClass", new PropertyEditorSupport() {
                public void setAsText(String text) {
                        Integer class_id = Integer.parseInt(text);
                        SchoolClass schoolClass = (SchoolClass) schoolClassService.getSchoolClassById(class_id);
                        setValue(schoolClass);
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


        binder.registerCustomEditor(List.class, "roles", new CustomCollectionEditor(List.class) {

            protected Object convertElement(Object element) {
                if (element != null) {
                    Integer TRId = Integer.parseInt(element.toString());
                    TRole tRole = (TRole) tRoleService.getTRoleById(TRId);
                    return tRole;

                }
                return null;
            }

        });
    }
}
