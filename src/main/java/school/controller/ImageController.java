package school.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import school.entity.Image;
import school.service.interfaces.ImageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

/**
 * Created by Cheshire on 23.09.2016.
 */
@Controller
public class ImageController {

    CommonsMultipartResolver multipartResolver;
    @Autowired
    public void setCommonsMultipartResolver(CommonsMultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }

    ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }


    @RequestMapping(method=RequestMethod.GET, value="/image/{imgId}")
    public void getImage(Model model, @PathVariable("imgId") int imgId, HttpServletResponse response)
            throws ServletException, IOException {
        //проверка на NULL
        if (imgId == 0) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Image image = this.imageService.getImageById(imgId);
        if (image == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" +image.getName()+ "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType(image.getContentType());
            IOUtils.copy(image.getContent().getBinaryStream(), out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
