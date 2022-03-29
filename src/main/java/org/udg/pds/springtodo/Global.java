package org.udg.pds.springtodo;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.service.UsuariService;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class Global {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private MinioClient minioClient;

    private final Logger logger = LoggerFactory.getLogger(Global.class);

    @Autowired
    private
    UsuariService userService;

    @Autowired
    private Environment environment;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${todospring.minio.url:}")
    private String minioURL;

    @Value("${todospring.minio.access-key:}")
    private String minioAccessKey;

    @Value("${todospring.minio.secret-key:}")
    private String minioSecretKey;

    @Value("${todospring.minio.bucket:}")
    private String minioBucket;

    @Value("${todospring.base-url:#{null}}")
    private String BASE_URL;

    @Value("${todospring.base-port:8080}")
    private String BASE_PORT;


    @PostConstruct
    void init() {

        logger.info(String.format("Starting Minio connection to URL: %s", minioURL));
        try {
            minioClient = MinioClient.builder()
                                     .endpoint(minioURL)
                                     .credentials(minioAccessKey, minioSecretKey)
                                     .build();
        } catch (Exception e) {
            logger.warn("Cannot initialize minio service with url:" + minioURL + ", access-key:" + minioAccessKey + ", secret-key:" + minioSecretKey);
        }

        if (minioBucket.equals("")) {
            logger.warn("Cannot initialize minio bucket: " + minioBucket);
            minioClient = null;
        }

        if (BASE_URL == null) BASE_URL = "http://localhost";
        BASE_URL += ":" + BASE_PORT;

        initData();
    }

   private void initData() {

        if (activeProfile.equals("dev")) {
            logger.info("Starting populating database ...");

            Usuari user1 = new Usuari("Gerard","geriloza@gmail.com","1234","El millor",false);
            Usuari user2 = new Usuari("Nossa","nossa@gmail.com","password","El git developer",false);
            Usuari user3 = new Usuari("David","divad@gmail.com","muntanya","El frontEnd developer",true);
            Usuari user4 = new Usuari("Marc","arnau@gmail.com","arnau","El papa",false);
            Usuari user5 = new Usuari("Carla","davesa@gmail.com","4321","La secretaria",false);
            Usuari user6 = new Usuari("Bernat","berni@gmail.com","computacio","El que treballa a Haribo",false);
            userService.guardarUsuari(user1);
            userService.guardarUsuari(user2);
            userService.guardarUsuari(user3);
            userService.guardarUsuari(user4);
            userService.guardarUsuari(user5);
            userService.guardarUsuari(user6);

        }

    }

    public MinioClient getMinioClient() {
        return minioClient;
    }

    public String getMinioBucket() {
        return minioBucket;
    }

    public String getBaseURL() {
        return BASE_URL;
    }
}
