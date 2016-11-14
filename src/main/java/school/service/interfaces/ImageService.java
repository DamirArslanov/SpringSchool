package school.service.interfaces;


import org.springframework.web.multipart.MultipartFile;
import school.entity.Image;

import java.util.List;

/**
 * Created by Cheshire on 23.09.2016.
 */
public interface ImageService {

    public Image addImage(MultipartFile file);

    public void updateImage(Image image);

    public Image getImageById(int id);

    public List<Image> listImages();

    public void removeImage(int id);
}
