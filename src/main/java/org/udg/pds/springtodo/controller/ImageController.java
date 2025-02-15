package org.udg.pds.springtodo.controller;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.entity.Usuari;
import org.udg.pds.springtodo.service.UsuariService;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.UUID;

@RequestMapping(path = "/images")
@RestController
public class ImageController extends BaseController {

    @Autowired
    Global global;

    @Autowired
    UsuariService usuariService;

    @PostMapping("")
    public String upload(HttpSession session,
                         @RequestParam("file") MultipartFile file) {

        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null)
            throw new ControllerException("Minio client not configured");

        try {
            InputStream istream = file.getInputStream();
            String contentType = file.getContentType();
            UUID imgName = UUID.randomUUID();

            String objectName = imgName + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(global.getMinioBucket())
                    .object(objectName)
                    .stream(istream, -1, 10485760)
                    .build());

            return String.format("\"%s\"", "http://localhost:8080/images/" + objectName);
        } catch (Exception e) {
            throw new ControllerException("Error saving file: " + e.getMessage());
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("filename") String filename) {

        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null)
            throw new ControllerException("Minio client not configured");

        try {
            InputStream file = minioClient.getObject(global.getMinioBucket(), filename);
            InputStreamResource body = new InputStreamResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(filename)));
            return ResponseEntity.ok().headers(headers).body(body);

        } catch (Exception e) {
            throw new ControllerException("Error downloading file: " + e.getMessage());
        }
    }
    @DeleteMapping("/{filename}")
    public void deleteImage(@PathVariable("filename") String filename){
        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null)
            throw new ControllerException("Minio client not configured");

        try{
            minioClient.removeObject(global.getMinioBucket(),filename);
        }catch (Exception e) {
            throw new ControllerException("Error deleting file: " + e.getMessage());
        }

    }
}




