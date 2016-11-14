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
import school.entity.Form.SearchForm;
import school.service.interfaces.ChildrenService;
import school.service.interfaces.LessonService;
import school.service.interfaces.RatingService;
import school.service.interfaces.SubjectService;


import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by ArslanovDamir on 28.09.2016.
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

    @RequestMapping(value = "/childreninfo/childrenratings/{childrenID}", method = RequestMethod.POST)
    public String getRatingByDate(@PathVariable("childrenID") int childrenID,
                                  @ModelAttribute("LessonSearchForm") SearchForm searchForm,
                                  Model model){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Integer.parseInt(searchForm.getMonth()));
        calendar.set(Calendar.YEAR, Integer.parseInt(searchForm.getYear()));
        date = calendar.getTime();
        List<Rating> ratings = new LinkedList<>();
        ratings = this.ratingService.getRatingsByChildren(childrenID, date);
        Set<Subject> subjectSet = new LinkedHashSet<>();
        if (!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                subjectSet.add(rating.getSubject());
            }
        }
        model.addAttribute("subjectSet", subjectSet);
        model.addAttribute("ratings", ratings);

        Map<String, String> selectMonth = new LinkedHashMap<>();
        selectMonth.put("0", "Январь");
        selectMonth.put("1", "Февраль");
        selectMonth.put("2", "Март");
        selectMonth.put("3", "Апрель");
        selectMonth.put("4", "Май");
        selectMonth.put("5", "Июнь");
        selectMonth.put("6", "Июль");
        selectMonth.put("7", "Август");
        selectMonth.put("8", "Сентябрь");
        selectMonth.put("9", "Октябрь");
        selectMonth.put("10", "Ноябрь");
        selectMonth.put("11", "Декабрь");
        model.addAttribute("selectMonth", selectMonth);

        model.addAttribute("children", this.childrenService.getChildrenById(childrenID));
        // 0_O
        LocalDate localDate = LocalDate.now();
        model.addAttribute("todayDate", localDate);
        model.addAttribute("now", date);

        return "childrenratings";
    }

    @RequestMapping(value = "/childreninfo/childrenratings/{childrenID}", method = RequestMethod.GET)
    public String getChildrenRatings(@PathVariable("childrenID") int childrenID, Model model) {
        List<Rating> ratings = new ArrayList<>();
        Set<Subject> subjectSet = new LinkedHashSet<>();
        ratings = this.ratingService.getRatingsByChildren(childrenID, new Date());
        // Сортировка на HQL в базе или Collection
//        Collections.sort(ratings, (o1, o2) -> o1.getSubject().getSub_name().compareTo(o2.getSubject().getSub_name()));
        if (!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                subjectSet.add(rating.getSubject());
            }
        }

        // Сортировка на HQL в базе или Collection
//        Collections.sort(ratings, (o1, o2) -> o1.getRt_Date().compareTo(o2.getRt_Date()));

        Map<String, String> selectMonth = new LinkedHashMap<>();
        selectMonth.put("0", "Январь");
        selectMonth.put("1", "Февраль");
        selectMonth.put("2", "Март");
        selectMonth.put("3", "Апрель");
        selectMonth.put("4", "Май");
        selectMonth.put("5", "Июнь");
        selectMonth.put("6", "Июль");
        selectMonth.put("7", "Август");
        selectMonth.put("8", "Сентябрь");
        selectMonth.put("9", "Октябрь");
        selectMonth.put("10", "Ноябрь");
        selectMonth.put("11", "Декабрь");

        model.addAttribute("selectMonth", selectMonth);
        model.addAttribute("LessonSearchForm", new SearchForm());
        model.addAttribute("subjectSet", subjectSet);
        model.addAttribute("ratings", ratings);
        model.addAttribute("children", this.childrenService.getChildrenById(childrenID));

        // 0_O
        LocalDate localDate = LocalDate.now();
        model.addAttribute("todayDate", localDate);
        model.addAttribute("now", new Date());

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


    @RequestMapping("/lesson/editrating/{rt_id}")
    public String editLessonRating(@PathVariable("rt_id") int rt_id, Model model) {
        Rating rating = (Rating) this.ratingService.getRatingById(rt_id);
        Map<Integer, String> selectMap = new LinkedHashMap<>();
        selectMap.put(0, "Отсутствовал");
        selectMap.put(1, "Присутствовал");
        selectMap.put(2, "2");
        selectMap.put(3, "3");
        selectMap.put(4, "4");
        selectMap.put(5, "5");
        model.addAttribute("selectMap", selectMap);
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

