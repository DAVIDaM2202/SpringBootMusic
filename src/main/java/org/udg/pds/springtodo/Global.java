package org.udg.pds.springtodo;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.Album;
import org.udg.pds.springtodo.entity.Artista;
import org.udg.pds.springtodo.entity.Canco;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.service.AlbumService;
import org.udg.pds.springtodo.service.ArtistaService;
import org.udg.pds.springtodo.service.CancoService;
import org.udg.pds.springtodo.service.UsuariService;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class Global {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private MinioClient minioClient;

    private final Logger logger = LoggerFactory.getLogger(Global.class);

    @Autowired
    private
    UsuariService userService;

    @Autowired
    private
    ArtistaService artistaService;

    @Autowired
    private
    AlbumService albumService;


    @Autowired
    private
    CancoService cancoService;

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

            //CREACIÓ D'USUARIS
            Usuari user1 = new Usuari("Gerard","geriloza@gmail.com","1234","El millor",false,"http://localhost:8080/images/gerard.jpg");
            Usuari user2 = new Usuari("Nossa","nossa@gmail.com","password","El git developer",false,"http://localhost:8080/images/anonim.JPG");
            Usuari user3 = new Usuari("David","divad@gmail.com","muntanya","El frontEnd developer",true,"http://localhost:8080/images/anonim.JPG");
            Usuari user4 = new Usuari("Marc","arnau@gmail.com","arnau","El papa",false,"http://localhost:8080/images/anonim.JPG");
            Usuari user5 = new Usuari("Carla","davesa@gmail.com","4321","La secretaria",false,"http://localhost:8080/images/anonim.JPG");
            Usuari user6 = new Usuari("Eminem","eminem@gmail.com","rapGod","http://localhost:8080/images/eminem.jpg");
            Usuari user7 = new Usuari("Bad Bunny","badbunny@gmail.com","conejoMalo","http://localhost:8080/images/bunny.JPG");
            Usuari user8 = new Usuari("2pac","2pac@gmail.com","biggie","http://localhost:8080/images/tupac.JPG");
            Usuari user9 = new Usuari("The Notorius B.I.G","biggie@gmail.com","2pac","http://localhost:8080/images/biggie.JPG");
            Usuari user10 = new Usuari("Ed Sheeran","ed@gmail.com","irish","http://localhost:8080/images/sheeran.jpg");


            userService.guardarUsuari(user1);
            userService.guardarUsuari(user2);
            userService.guardarUsuari(user3);
            userService.guardarUsuari(user4);
            userService.guardarUsuari(user5);
            userService.guardarUsuari(user6);
            userService.guardarUsuari(user7);
            userService.guardarUsuari(user8);
            userService.guardarUsuari(user9);
            userService.guardarUsuari(user10);


            // CREACIÓ D'ARTISTES
            Artista artista1 = new Artista(user4);
            artistaService.guardarArtista(artista1);
            user4.setJoComArtista(artista1);

            Artista artista2 = new Artista(user6);
            artistaService.guardarArtista(artista2);
            user6.setJoComArtista(artista2);

            Artista artista3 = new Artista(user7);
            artistaService.guardarArtista(artista3);
            user7.setJoComArtista(artista3);

            Artista artista4 = new Artista(user8);
            artistaService.guardarArtista(artista4);
            user8.setJoComArtista(artista4);

            Artista artista5 = new Artista(user9);
            artistaService.guardarArtista(artista5);
            user9.setJoComArtista(artista5);

            Artista artista6 = new Artista(user10);
            artistaService.guardarArtista(artista6);
            user10.setJoComArtista(artista6);

            //CREACIÓ D'ALBUMS

            Album album1 = new Album("The Eminem Show","http://localhost:8080/images/eminemShow.JPG","",artista2);
            Album album2 = new Album("Music To Be Murdered By","http://localhost:8080/images/musicToBeMurdered.JPG","",artista2);

            Album album3 = new Album("Oasis","http://localhost:8080/images/oasis.JPG","",artista3);
            Album album4 = new Album("YHLQMDLG","http://localhost:8080/images/yhlqmdlg.JPG","",artista3);

            Album album5 = new Album("All Eyez On Me","http://localhost:8080/images/allEyezOnMe.JPG","",artista4);
            Album album6 = new Album("Me Against The World","http://localhost:8080/images/meAgainstTheWorld.JPG","",artista4);

            Album album7 = new Album("Ready to Die","http://localhost:8080/images/readyToDie.JPG","",artista5);
            Album album8 = new Album("Life After Death","http://localhost:8080/images/lifeAfterDeath.JPG","",artista5);

            Album album9 = new Album("=","http://localhost:8080/images/equal.JPG","",artista6);
            Album album10 = new Album("x","http://localhost:8080/images/x.JPG","",artista6);

            albumService.guardarAlbum(album1);
            albumService.guardarAlbum(album2);
            albumService.guardarAlbum(album3);
            albumService.guardarAlbum(album4);
            albumService.guardarAlbum(album5);
            albumService.guardarAlbum(album6);
            albumService.guardarAlbum(album7);
            albumService.guardarAlbum(album8);
            albumService.guardarAlbum(album9);
            albumService.guardarAlbum(album10);

            //CREACIO DE CANCONS D'ALBUMS

            Canco canco1 = new Canco("Without Me","Hip Hop",2002,"http://localhost:8080/images/eminemShow.JPG",artista2,album1);
            Canco canco2 = new Canco("Soldier","Hip Hop",2002,"http://localhost:8080/images/eminemShow.JPG",artista2,album1);
            Canco canco3 = new Canco("Superman","Hip Hop",2002,"http://localhost:8080/images/eminemShow.JPG",artista2,album1);

            Canco canco4 = new Canco("Godzilla (feat.Juice WRLD)","Hip Hop",2020,"http://localhost:8080/images/musicToBeMurdered.JPG",artista2,album2);
            Canco canco5 = new Canco("Stepdad","Hip Hop",2020,"http://localhost:8080/images/musicToBeMurdered.JPG",artista2,album2);
            Canco canco6 = new Canco("Farewell","Hip Hop",2020,"http://localhost:8080/images/musicToBeMurdered.JPG",artista2,album2);

            Canco canco7 = new Canco("MOJAITA","Reggaeton",2019,"http://localhost:8080/images/oasis.JPG",artista3);
            Canco canco8 = new Canco("ODIO","Reggaeton",2019,"http://localhost:8080/images/oasis.JPG",artista3);
            Canco canco9 = new Canco("UN PESO","Reggaeton",2019,"http://localhost:8080/images/oasis.JPG",artista3);

            Canco canco10 = new Canco("Yo Perreo Sola","Reggaeton",2020,"http://localhost:8080/images/yhlqmdlg.JPG",artista3);
            Canco canco11 = new Canco("La Difícil","Reggaeton",2020,"http://localhost:8080/images/yhlqmdlg.JPG",artista3);
            Canco canco12 = new Canco("Safaera","Reggaeton",2020,"http://localhost:8080/images/yhlqmdlg.JPG",artista3);

            Canco canco13 = new Canco("Ambitionz Az A Ridah","Hip Hop",1996,"http://localhost:8080/images/allEyezOnMe.JPG",artista4,album5);
            Canco canco14 = new Canco("Only God Can Judge Me","Hip Hop",1996,"http://localhost:8080/images/allEyezOnMe.JPG",artista4,album5);
            Canco canco15 = new Canco("Heartz Of Men","Hip Hop",1996,"http://localhost:8080/images/allEyezOnMe.JPG",artista4,album5);

            Canco canco16 = new Canco("Temptations","Hip Hop",1997,"http://localhost:8080/images/meAgainstTheWorld.JPG",artista4,album6);
            Canco canco17 = new Canco("Dear Mama","Hip Hop",1997,"http://localhost:8080/images/meAgainstTheWorld.JPG",artista4,album6);
            Canco canco18 = new Canco("It Ain't Easy","Hip Hop",1997,"http://localhost:8080/images/meAgainstTheWorld.JPG",artista4,album6);

            Canco canco19 = new Canco("Gimme the Loot","Hip Hop",1994,"http://localhost:8080/images/readyToDie.JPG",artista5,album7);
            Canco canco20 = new Canco("Juicy","Hip Hop",1994,"http://localhost:8080/images/readyToDie.JPG",artista5,album7);
            Canco canco21 = new Canco("Big Poppa","Hip Hop",1994,"http://localhost:8080/images/readyToDie.JPG",artista5,album7);

            Canco canco22 = new Canco("Hypnotize","Hip Hop",1997,"http://localhost:8080/images/lifeAfterDeath.JPG",artista5,album8);
            Canco canco23 = new Canco("I Got a Story to Tell","Hip Hop",1997,"http://localhost:8080/images/lifeAfterDeath.JPG",artista5,album8);
            Canco canco24 = new Canco("Miss U","Hip Hop",1997,"http://localhost:8080/images/lifeAfterDeath.JPG",artista5,album8);

            Canco canco25 = new Canco("Shivers","Pop",2021,"http://localhost:8080/images/equal.JPG",artista6,album9);
            Canco canco26 = new Canco("Bad Habits","Pop",2021,"http://localhost:8080/images/equal.JPG",artista6,album9);
            Canco canco27 = new Canco("Sandman","Pop",2021,"http://localhost:8080/images/equal.JPG",artista6,album9);

            Canco canco28 = new Canco("Happier","Pop",2017,"http://localhost:8080/images/x.JPG",artista6,album10);
            Canco canco29 = new Canco("Perfect","Pop",2017,"http://localhost:8080/images/x.JPG",artista6,album10);
            Canco canco30 = new Canco("Nancy Mulligan","Pop",2017,"http://localhost:8080/images/x.JPG",artista6,album10);


            //CREACIÓ SINGLES
            Canco single1 = new Canco("Rap God","Hip Hop",2013,"http://localhost:8080/images/rapGod.JPG",artista2);
            Canco single2 = new Canco("Yonaguni","Reggaeton",2021,"http://localhost:8080/images/yonaguni.JPG",artista3);

            cancoService.guardarCanco(canco1);
            cancoService.guardarCanco(canco2);
            cancoService.guardarCanco(canco3);
            cancoService.guardarCanco(canco4);
            cancoService.guardarCanco(canco5);
            cancoService.guardarCanco(canco6);
            cancoService.guardarCanco(canco7);
            cancoService.guardarCanco(canco8);
            cancoService.guardarCanco(canco9);
            cancoService.guardarCanco(canco10);
            cancoService.guardarCanco(canco11);
            cancoService.guardarCanco(canco12);
            cancoService.guardarCanco(canco13);
            cancoService.guardarCanco(canco14);
            cancoService.guardarCanco(canco15);
            cancoService.guardarCanco(canco16);
            cancoService.guardarCanco(canco17);
            cancoService.guardarCanco(canco18);
            cancoService.guardarCanco(canco19);
            cancoService.guardarCanco(canco20);
            cancoService.guardarCanco(canco21);
            cancoService.guardarCanco(canco22);
            cancoService.guardarCanco(canco23);
            cancoService.guardarCanco(canco24);
            cancoService.guardarCanco(canco25);
            cancoService.guardarCanco(canco26);
            cancoService.guardarCanco(canco27);
            cancoService.guardarCanco(canco28);
            cancoService.guardarCanco(canco29);
            cancoService.guardarCanco(canco30);
            cancoService.guardarCanco(single1);
            cancoService.guardarCanco(single2);

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
