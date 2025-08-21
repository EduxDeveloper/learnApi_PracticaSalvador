package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class ColudinaryServices {
    //1.Constante para definir el tamaño maximo permitido para los archivos(5mb)
    private static final long MAX_FLIE_SIZE = 5 * 1024 * 1024;
    //2.Extensiones de archivo permitidas para subir a cloudinary
    private static final String[] ALLOWED_EXTENSIONS ={".jpg",".png",".jpeg",".gif"};
    //3.Cliente  de cloudinary inyectando como dependencia
    private final Cloudinary cloudinary;

    public ColudinaryServices(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagenes a la raiz claudinary
    public String uploadImage(MultipartFile file) throws IOException{
        validateImage(file);
        Map<?,?>uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "ResourceType","auto","quality","auto:good"
        ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException{
        validateImage(file);
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "public_id", uniqueFilename,
                "use_filename", false,
                "unique_filename", false,
                "overwrite", false,
                "resource_type", "auto",
                "quality", "auto:good"
        );

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),options);
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        //1.Verificar si el archivo esta vacio
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo no puede estar vacio");
        //2.Verificar si el tamaño del archivo excede el limite permetido
        if (file.getSize() > MAX_FLIE_SIZE) throw new IllegalArgumentException("El tamaño del archivo no puede exceder los 5 megabyes");
        String orginalFilename = file.getOriginalFilename();
        //Valida el nombre original
        if (orginalFilename == null)throw new IllegalArgumentException("Nombre de archivo no valido");
        String extension = orginalFilename.substring(orginalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)) throw new IllegalArgumentException("Solo se permiten archivos .jpg, png o jpeg");
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe ser una imagen valida");
    }
    //Subir imagenes a una carpeta de Cloudinary


}
