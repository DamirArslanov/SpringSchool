package school.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by ArslanovDamir on 23.09.2016.
 */
@Component
@Entity
@Table(name = "image", catalog = "school")
public class Image {

    @Id
    @Column(name = "imgID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "imgName")
    String name;

    @Column(name = "imgContent")
    @Lob
    Blob content;

    @Column(name="CONTENTTYPE")
    private String contentType;

    public Image() {
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
