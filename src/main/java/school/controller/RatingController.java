package school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import school.entity.*;
import school.service.interfaces.ChildrenService;
import school.service.interfaces.LessonService;
import school.service.interfaces.RatingService;
import school.service.interfaces.SubjectService;


import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cheshire on 28.09.2016.
 */
@Controller
public class RatingController {

    private SubjectService subjectService;

    @Autowired(required = true)
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    private RatingService ratingService;

    @Autowired(required = true)
    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    private ChildrenService childrenService;

    @Autowired(required = true)
    public void setChildrenService(ChildrenService childrenService) {
        this.childrenService = childrenService;
    }


    private LessonService lessonService;

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }


//
//
//
//
//
//    @ModelAttribute("subjects")
//    public List<Subject> getAllSubjects() {
//        return subjectService.listSubjects();
//    }
//
//
//    @ModelAttribute("childrens")
//    public List<Children> getAllChildrens() {
//        return childrenService.listChildrens();
//    }
//
//
//    @ModelAttribute("listRatings")
//    public List<Rating> getAllRatings() {
//        return this.ratingService.listRatings();
//    }



    @RequestMapping(value = "/childreninfo/childrenratings/{childrenID}", method = RequestMethod.GET)
    public String getChildrenRatings(@PathVariable("childrenID") int childrenID, Model model) {
        List<Rating> ratings = new ArrayList<>();
        Set<Subject> subjectSet = new LinkedHashSet<>();
        ratings = this.ratingService.getRatingsByChildren(childrenID);
        Collections.sort(ratings, (o1, o2) -> o1.getSubject().getSub_name().compareTo(o2.getSubject().getSub_name()));
        if (!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                subjectSet.add(rating.getSubject());
            }
        }
        Collections.sort(ratings, (o1, o2) -> o1.getRt_Date().compareTo(o2.getRt_Date()));
        model.addAttribute("subjectSet", subjectSet);
        model.addAttribute("ratings", ratings);
        model.addAttribute("children", this.childrenService.getChildrenById(childrenID));
        return "childrenratings";
    }




    @RequestMapping(value = "ratings/{lesson_id}", method = RequestMethod.GET)
    public String listRatings(@PathVariable("lesson_id") int lesson_id, Model model) {
        model.addAttribute("lessonRatings", this.ratingService.getRatingsByLesson(lesson_id));
        model.addAttribute("rating", new Rating());
        return "ratings";
    }

    @RequestMapping(value = "ratingsClass", method = RequestMethod.GET)
    public String listRatingsClass(Model model) {
        model.addAttribute("rating", new Rating());
        return "ratingsClass";
    }

    @RequestMapping(value = "/ratings/add", method = RequestMethod.POST)
    public String addRating(@Valid @ModelAttribute("rating") Rating rating,
                            BindingResult bindingResult) {

        int backURL = rating.getLesson().getLess_id();

        if (bindingResult.hasErrors()) {
            return "ratings";
        }

        if (rating.getRt_id() == 0) {
            this.ratingService.addRating(rating);
        }else {
            this.ratingService.updateRating(rating);
        }
        return "redirect:/lesson/update/"+ backURL;
    }


    @RequestMapping(value = "/ratings/add/{lessId}/{childId}")
    public String addRatingByLesson(@PathVariable("lessId") int lid,
                                      @PathVariable("childId") int cid,
                                      Model model) {



        Lesson lesson = (Lesson) this.lessonService.getLessonById(lid);
        Subject subject = (Subject) lesson.getSchedule().getSubject();
        model.addAttribute("subject", subject);
        Children children = (Children) this.childrenService.getChildrenById(cid);
        model.addAttribute("children", children);

        Rating rating = new Rating();
        rating.setChildren(children);
        rating.setSubject(subject);
        rating.setLesson(lesson);
        rating.setRt_Date(lesson.getLess_Date());
        model.addAttribute("rating", rating);
        return "addrating";

    }

    @RequestMapping("/lesson/editrating/{rt_id}")
    public String editLessonRating(@PathVariable("rt_id") int rt_id, Model model) {
        Rating rating = (Rating) this.ratingService.getRatingById(rt_id);
        model.addAttribute("rating", rating);
        return "ratings";
    }

    @RequestMapping("/ratings/delete/{id}")
    public String removeRating(@PathVariable("id") int id) {
        this.ratingService.removeRating(id);
        return "redirect:/ratings";
    }


    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        binder.registerCustomEditor(Children.class, "children", new PropertyEditorSupport() {
            public void setAsText(String text) {
                Integer id = Integer.parseInt(text);
                Children children = (Children) childrenService.getChildrenById(id);
                setValue(children);

            }

            public String getAsText() {
                Object value = getValue();
                if (value != null) {
                    Children children = (Children) value;

                    return children.getCh_name();
                }
                return null;
            }
        });



        binder.registerCustomEditor(Subject.class, "subject", new PropertyEditorSupport() {
            public void setAsText(String text) {
                Integer sub_id = Integer.parseInt(text);
                Subject subject = (Subject) subjectService.getSubjectById(sub_id);
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

