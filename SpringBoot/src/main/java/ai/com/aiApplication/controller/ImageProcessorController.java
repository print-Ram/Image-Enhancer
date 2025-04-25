package ai.com.aiApplication.controller;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.com.aiApplication.service.ImageProcessorService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/image")
public class ImageProcessorController {

    @Autowired
    private ImageProcessorService imageProcessorService;
    
    @GetMapping("/test")
    public String testApi() {
        return "Image controller is working!";
    }


    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return imageProcessorService.enhanceImage(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/cartoon")
    public ResponseEntity<byte[]> cartoonify(@RequestParam("file") MultipartFile file) {
        try {
            return imageProcessorService.cartoonifyImage(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
