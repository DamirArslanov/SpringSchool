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

    private final int DEFAULT_BUFFER_SIZE = 1024000;

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


    @RequestMapping("/manageallimages")
    public String listImages(Model model) {
        model.addAttribute("image", new Image());
        model.addAttribute("imageList", this.imageService.listImages());
        return "/manageallimages";
    }

    @RequestMapping(value = "/addImage", method = RequestMethod.POST)
    public String addImage(@RequestParam("file")MultipartFile file) {
        if (!file.isEmpty()) {
            this.imageService.addImage(file);
        }
        return "redirect:/manageallimages";
    }

    @RequestMapping(value="/deleteImage/{imgId}")
    public String deleteImage(@PathVariable("imgId") Integer id){
        this.imageService.removeImage(id);
        return "redirect:/manageallimages";
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
//        response.reset();
//        response.setBufferSize(DEFAULT_BUFFER_SIZE);
//        response.setContentType(image.getContentType());
//
//
//        // Prepare streams.
//        BufferedInputStream input = null;
//        BufferedOutputStream output = null;
//
//        try {
//            // Open streams.
//            try {
//                input = new BufferedInputStream(image.getContent().getBinaryStream(), DEFAULT_BUFFER_SIZE);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
//
//            // Write file contents to response.
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
//            while ((length = input.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//        } finally {
//            // Gently close streams.
//            close(output);
//            close(input);
//        }
//    }
//
//    // Helper (can be refactored to public utility class)
//    private static void close(Closeable resource) {
//        if (resource != null) {
//            try {
//                resource.close();
//            } catch (IOException e) {
//                // Do your thing with the exception. Print it, log it or mail it.
//                e.printStackTrace();
//            }
//        }
    }
}
