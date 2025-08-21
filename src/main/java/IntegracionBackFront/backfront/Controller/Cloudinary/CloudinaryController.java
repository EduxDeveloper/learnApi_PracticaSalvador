package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.ColudinaryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController @RequestMapping("/Api/Image") @CrossOrigin
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

    @PostMapping("/Upload-to-folder")
    public ResponseEntity<?> uploadImageToFolder(@RequestParam("image")MultipartFile file, String folder){
       try {
           String imagenUrl = service.uploadImage(file, folder);
           return ResponseEntity.ok(Map.of("Message","Imagen subida","Url",imagenUrl));
       }catch (IOException e){
           return ResponseEntity.internalServerError().body("Error al subir la imagen");
       }
    }



}
