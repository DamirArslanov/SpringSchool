package school.dao;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import school.dao.interfaces.ImageDao;
import school.entity.Image;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;


/**
 * Created by Cheshire on 23.09.2016.
 */
@Repository
public class ImageDaoImpl implements ImageDao {


    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Image addImage(MultipartFile file) {
        Image image = new Image();
        Blob blob = null;

        image.setName(file.getOriginalFilename());
        image.setContentType(file.getContentType());


        try {
            LobCreator lobCreator = Hibernate.getLobCreator(sessionFactory.getCurrentSession());
            blob = lobCreator.createBlob(file.getInputStream(), file.getSize());
        }catch (IOException e) {
            e.printStackTrace();
        }
        image.setContent(blob);
        sessionFactory.getCurrentSession().save(image);
        return image;
    }





    public List<Image> listImages() {
        Session session = sessionFactory.getCurrentSession();
        List<Image> listImages = session.createQuery("FROM Image ").list();
        return listImages;
    }

    public void removeImage(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Image image = (Image) session.load(Image.class, new Integer(id));
        if (image != null) {
            session.delete(image);
        }
    }

    public void updateImage(Image image) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(image);
    }

    public Image getImageById(int id) {
        return (Image) sessionFactory.getCurrentSession().get(Image.class, id);
    }
}
