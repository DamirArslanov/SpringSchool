package school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import school.dao.interfaces.ImageDao;
import school.entity.Image;
import school.service.interfaces.ImageService;

import java.util.List;

/**
 * Created by Cheshire on 23.09.2016.
 */
@Service
public class ImageServiceImpl implements ImageService {

    ImageDao imageDao;

    @Autowired
    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }



//    @Override
//    @Transactional
//    public void addImage(MultipartFile file) {
//        imageDao.addImage(file);
//    }
    @Override
    @Transactional
    public Image addImage(MultipartFile file) {
        Image image = imageDao.addImage(file);

        return image;
    }

    @Override
    @Transactional
    public void updateImage(Image image) {
        imageDao.updateImage(image);
    }

    @Override
    @Transactional
    public Image getImageById(int id) {
        return imageDao.getImageById(id);
    }

    @Override
    @Transactional
    public List<Image> listImages() {
        return imageDao.listImages();
    }

    @Override
    @Transactional
    public void removeImage(int id) {
        imageDao.removeImage(id);
    }
}
