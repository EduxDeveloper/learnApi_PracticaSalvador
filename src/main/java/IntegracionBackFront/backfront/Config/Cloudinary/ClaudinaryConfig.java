package IntegracionBackFront.backfront.Config.Cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClaudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
        //crear un objeto para leer las variables del archivo .env
        Dotenv dotenv = Dotenv.load();
        //Crear un map para guardar la informacion necesaria para Cloudinary
        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLOUDINARY_APIKEY"));
        config.put("api_secret", dotenv.get("CLOUDINARY_API_SECRET"));

        return  new Cloudinary(config);
    }


}
