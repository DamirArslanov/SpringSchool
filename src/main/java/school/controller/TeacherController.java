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
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import school.entity.Children;
import school.entity.Image;
import school.entity.SchoolClass;
import school.entity.Teacher;
import school.security.TRole;
import school.security.TRoleService;
//import school.security.teacherSecurity.TeacherSecurityService;
import school.security.teacherSecurity.TeacherSecurityService;
import school.service.interfaces.ImageService;
import school.service.interfaces.SchoolClassService;
import school.service.interfaces.TeacherService;
import school.service.interfaces.UserService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Cheshire on 19.09.2016.
 */
@Controller
public class TeacherController {


//УКАЖИ В ДИСПАТЧЕРЕ-СЁРВЛЕТ ЭТОТ БИН!!!!!!! Возможно заработает загрузка файла

    CommonsMultipartResolver multipartResolver;
    @Autowired
    public void setCommonsMultipartResolver(CommonsMultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }

    private final int DEFAULT_BUFFER_SIZE = 102400; // 10KB.
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

    private TeacherSecurityService teacherSecurityService;

    @Autowired
    public void setTeacherSecurityService(TeacherSecurityService teacherSecurityService) {
        this.teacherSecurityService = teacherSecurityService;
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
//    @ModelAttribute("users")
//    public List<User> getAllUsers() {
//        return userDao.findAll();
//    }

//    @ModelAttribute("listTeachers")
//    public List<Teacher> getAllTeachers(){
//        return teacherService.listTeachers();
//    }


//    @ModelAttribute("schoolClasses")
//    public List<SchoolClass> getAllClasses(){
//        return schoolClassService.listSchoolClass();
//    }


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

//    @RequestMapping(value = "/teachers/add", method = RequestMethod.POST)
//    public String addTeacher(@ModelAttribute("teacher") Teacher teacher, @RequestParam("file") MultipartFile file) {



        if (!file.isEmpty()) {
//            this.imageService.addImage(file);
            Image image = (Image) this.imageService.addImage(file);
            teacher.setImage(image);
        }
        if (file.isEmpty() & id != null) {
            teacher.setImage(this.imageService.getImageById(id));
        }

        if (teacher.getT_id() == 0) {  //почему не NULL? Да потому что это не INTEGER(любой другой класс), а int. Был бы INTEGER - был бы и NULL
            this.teacherService.addTeacher(teacher);
        }else {
            this.teacherService.updateTeacher(teacher);
        }
        return "redirect:/admin/teachers";
    }

//    @RequestMapping(value = "/teacherregistration", method = RequestMethod.GET)
//    public String teacherRegistrationG(Model model) {
//        model.addAttribute("teacher", new Teacher());
//
//        return "registration";
//    }
//
//    @RequestMapping(value = "/teacherregistration", method = RequestMethod.POST)
//    public String teacherRegistration(@ModelAttribute("teacher") Teacher teacher) {
//
//        this.teacherService.addTeacher(teacher);
//
//        this.teacherSecurityService.autoLogin(teacher.getUsername(), teacher.getPassword());
//
//        return "index";
//    }

//    @RequestMapping(value = "/teacherlogin", method = RequestMethod.GET)
//    public String login(Model model, String error, String logout) {
//        if (error != null) {
//            model.addAttribute("error", "Username or password is incorrect.");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "Logged out successfully.");
//        }
//
//        return "login";
//    }

    @ModelAttribute("roles")
    public List<TRole> getAllProjects() {
        return tRoleService.T_ROLES_LIST();
    }


    @RequestMapping("admin/teachers/delete/{id}")
    public String removeTeacher(@PathVariable("id") int id) {
        this.teacherService.removeTeacher(id);
        return "redirect:/admin/teachers";
    }

    @RequestMapping("/ajax")
    public  String getajax(){
        return "ajax";
    }

    @RequestMapping("/ajaxtest")
    public @ResponseBody
    Teacher getTime() {

//        Random rand = new Random();
//        float r = rand.nextFloat() * 100;
//        String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
//        System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
//        return result;
        Teacher teacher = new Teacher();
        teacher.setT_name("Cleopatra");
        return teacher;
    }





//    @RequestMapping(value = "admin/teachers/mod/{id}", method = RequestMethod.GET)
//    public Model updateModTeacher(@PathVariable("id") int id, Model model) {
//        System.out.println("******************НАС ВЫЗВАЛИ******************");
//        Teacher pt = (Teacher) this.teacherService.getTeacherById(id);
//        //ВРЕМЕННЫЙ И ВЫНУЖДЕННЫЙ КОД = PersistentBag
//        Teacher teacher = new Teacher();
//        teacher.setImage(pt.getImage());
//        teacher.setT_id(pt.getT_id());
//        teacher.setT_name(pt.getT_name());
//        teacher.setT_surname(pt.getT_surname());
//        teacher.setT_pname(pt.getT_pname());
//        if (pt.getTeacherPhone() != null) {
//            teacher.setTeacherPhone(pt.getTeacherPhone());
//        } else teacher.setTeacherPhone(null);
//        teacher.setUsername(pt.getUsername());
//        teacher.setPassword(pt.getPassword());
//        teacher.setSchoolClass(pt.getSchoolClass());
//        List<TRole> roles = new ArrayList<>();
//        List<TRole> temp = pt.getRoles();
//        for (TRole role : temp) {
//            roles.add(tRoleService.getTRoleById(role.getTRId()));
//        }
//        teacher.setRoles(roles);
//        model.addAttribute("teacher", teacher);
//        return model;
//    }


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
//                    if (!("null".equals(text))) {
                        Integer class_id = Integer.parseInt(text);
                        SchoolClass schoolClass = (SchoolClass) schoolClassService.getSchoolClassById(class_id);
                        setValue(schoolClass);
//                    }
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


//        binder.registerCustomEditor(TRole.class, "role", new PropertyEditorSupport() {
//            public void setAsText(String text) {
//                Integer TRId = Integer.parseInt(text);
//                TRole role = (TRole) tRoleService.getTRoleById(TRId);
//                setValue(role);
//
//            }
//
//            public String getAsText() {
//                Object value = getValue();
//                if (value != null) {
//                    TRole role = (TRole) value;
//
//                    return role.getRole();
//                }
//                return null;
//            }
//        });
        binder.registerCustomEditor(List.class, "roles", new CustomCollectionEditor(List.class) {

            protected Object convertElement(Object element) {
                if (element != null) {
                    Integer TRId = Integer.parseInt(element.toString());
                    System.out.println("***&&&&&&&&&&&&&&&&&&&&&СМООООООООООТРИИИИИИИ&&&&&&&&&&&&&&&&&&&&&&&*********");
                    System.out.println("***&&&&&&&&&&&&&&&&&&&&&&&&СМООООООООООТРИИИИИИИ&&&&&&&&&&&&&&&&&&&&*********");
                    System.out.println(TRId);
                    System.out.println("***&&&&&&&&&&&&&&&&&&&&&&&&&СМООООООООООТРИИИИИИИ&&&&&&&&&&&&&&&&&&&*********");
                    System.out.println("***&&&&&&&&&&&&&&&&&&&&&&&&&&СМООООООООООТРИИИИИИИ&&&&&&&&&&&&&&&&&&*********");
                    TRole tRole = (TRole) tRoleService.getTRoleById(TRId);
                    return tRole;

                }
                return null;
            }

        });
    }


//
//    @RequestMapping(method=RequestMethod.GET, value="/image/{imgId}")
//    public void getImage(Model model, @PathVariable("id") Integer imgId, HttpServletResponse response)
//            throws ServletException, IOException {
//        //проверка на NULL
//        if (imgId == null) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
//        Image image = this.imageService.getImageById(imgId);
//        if (image == null) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
//        response.reset();
//        response.setBufferSize(DEFAULT_BUFFER_SIZE);
//        response.setContentType("image/jpeg");
//
//        // Prepare streams.
//        BufferedInputStream input = null;
//        BufferedOutputStream output = null;
//
//        try {
//            // Open streams.
//            try {
//                input = new BufferedInputStream(image.getContent().getBinaryStream(), DEFAULT_BUFFER_SIZE);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
//
//            // Write file contents to response.
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
//            while ((length = input.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//        } finally {
//            // Gently close streams.
//            close(output);
//            close(input);
//        }
//    }
//    private static void close(Closeable resource) {
//        if (resource != null) {
//            try {
//                resource.close();
//            } catch (IOException e) {
//                // Do your thing with the exception. Print it, log it or mail it.
//                e.printStackTrace();
//            }
//        }
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/image/teacherImg/{imgID}")
//    public void getImage(Model model,
//                              @PathVariable("imgID") Integer imgID,
//                              HttpServletResponse response) throws ServletException, IOException {
//    /*
//     * Big thanks to BalusC for this part cf. his post on
//     * http://balusc.blogspot.ch/2007/04/imageservlet.html
//     */
//
//        // Check if ID is supplied to the request.
//        if (imgID == null) {
//            // Do your thing if the ID is not supplied to the request.
//            // Throw an exception, or send 404, or show default/warning image,
//            // or just ignore it.
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
//            return;
//        }
//
//        // Lookup Image by ImageId in database.
//        // Do your "SELECT * FROM Image WHERE ImageID" thing.
//        Image image = this.imageService.getImageById(imgID);
//
//
//        // Check if image is actually retrieved from database.
//        if (image == null) {
//            // Do your thing if the image does not exist in database.
//            // Throw an exception, or send 404, or show default/warning image,
//            // or just ignore it.
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
//            return;
//        }
//
//        // Init servlet response.
//        response.reset();
//        response.setBufferSize(DEFAULT_BUFFER_SIZE);
//        response.setContentType("image/jpeg");
//
//        // Prepare streams.
//        BufferedInputStream input = null;
//        BufferedOutputStream output = null;
//
//        try {
//            // Open streams.
//            try {
//                input = new BufferedInputStream(image.getContent().getBinaryStream(), DEFAULT_BUFFER_SIZE);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            output = new BufferedOutputStream(response.getOutputStream(),DEFAULT_BUFFER_SIZE);
//
//            // Write file contents to response.
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
//            while ((length = input.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//        } finally {
//            // Gently close streams.
//            close(output);
//            close(input);
//        }
//    }
//
//    // Helper (can be refactored to public utility class)
//    private static void close(Closeable resource) {
//        if (resource != null) {
//            try {
//                resource.close();
//            } catch (IOException e) {
//                // Do your thing with the exception. Print it, log it or mail
//                // it.
//                e.printStackTrace();
//            }
//        }
//    }
//


















}
