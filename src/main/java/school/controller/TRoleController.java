package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import school.security.TRole;
import school.security.TRoleService;

import java.util.List;

/**
 * Created by ArslanovDamir on 11.10.2016.
 */
@Controller
public class TRoleController {


    private TRoleService tRoleService;
    @Autowired
    public void settRoleService(TRoleService tRoleService) {
        this.tRoleService = tRoleService;
    }

    @ModelAttribute("roles")
    public List<TRole> getAllProjects() {
        return tRoleService.T_ROLES_LIST();
    }

    @RequestMapping(value = "admin/roles", method = RequestMethod.GET)
    public String listTeachers(Model model) {
        model.addAttribute("role", new TRole());
        model.addAttribute("roles", this.tRoleService.T_ROLES_LIST());
        return "teachers";
    }

}
