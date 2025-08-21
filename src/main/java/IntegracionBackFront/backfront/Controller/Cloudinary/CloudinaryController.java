package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.ColudinaryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController @RequestMapping("/Api/Image")
public class CloudinaryController {

    private final ColudinaryServices service;


    public CloudinaryController(ColudinaryServices service) {
        this.service = service;
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file){
        try {
            String imageUrl = service.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "Message","Imagen Subida Exitosamente",
                    "url", imageUrl//Cuando se vaya a cargar la imagen al prontend tenemos quep poner esta variable
            ));

        }catch (IOException e){
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }
}
