package school.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.*;

/**
 * Created by ArslanovDamir on 25.09.2016.
 */
@Component
@Entity
@Table(name = "subject", catalog = "school")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectId", unique = true, nullable = false)
    int sub_id;


    @Column(name = "subjectName", nullable = false)
    String sub_name;

    public Subject() {
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
}
