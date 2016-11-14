package school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import school.entity.Subject;
import school.service.interfaces.SubjectService;


import java.util.List;

/**
 * Created by ArslanovDamir on 26.09.2016.
 */
@Controller
public class SubjectController {

    private SubjectService subjectService;
    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ModelAttribute("subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.listSubjects();
    }


    @RequestMapping(value = "/admin/subjects", method = RequestMethod.GET)
    public String listSubjects(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("listSubjects", this.subjectService.listSubjects());
        return "subjects";
    }


    @RequestMapping(value = "/admin/subjects/add", method = RequestMethod.POST)
    public String addSubject(@ModelAttribute("subject") Subject subject) {
        if (subject.getSub_id() == 0) {
            this.subjectService.addSubject(subject);
        }else {
            this.subjectService.updateSubject(subject);
        }
        return "redirect:/admin/subjects";
    }

    @RequestMapping("/admin/subjects/edit/{id}")
    public String updateSubject(@PathVariable("id") int id, Model model) {
        model.addAttribute("subject", this.subjectService.getSubjectById(id));
        model.addAttribute("listSubjects", this.subjectService.listSubjects());
        return "subjects";
    }

    @RequestMapping("/admin/subjects/delete/{id}")
    public String removeSubject(@PathVariable("id") int id) {
        this.subjectService.removeSubject(id);
        return "redirect:/admin/subjects";
    }
}


