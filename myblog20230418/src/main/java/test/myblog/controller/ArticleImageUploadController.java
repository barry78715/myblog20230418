package test.myblog.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import test.myblog.model.*;
import com.google.gson.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/upload")
public class ArticleImageUploadController {
	
	@Autowired
    private ServletContext servletContext;

    @PostMapping("/image")
    public ResponseEntity<String> handleImageUpload(@RequestParam("upload") MultipartFile file, HttpServletRequest request) {
        try {
//        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        	HttpHeaders headers = new HttpHeaders();
//        	if (authentication != null && authentication.isAuthenticated()) {
//        		
//     		    headers.set("X-Frame-Options", "SAMEORIGIN");
//        	}
            // 將文件保存到磁盤上
            byte[] bytes = file.getBytes();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedDateTime = now.format(formatter);
            String staticPath = servletContext.getRealPath("/upload/article/") + formattedDateTime + file.getOriginalFilename();
            System.out.println(staticPath);
            BufferedOutputStream stream = new BufferedOutputStream(
    				new FileOutputStream(new File(staticPath)));
    		stream.write(bytes);
    		stream.flush();
    		stream.close();
    		
    		CKEditorResponse response = new CKEditorResponse();
		    response.setUploaded(true);
		    response.setFileName(staticPath);
		    response.setUrl("http://localhost:8080/upload/article/" + formattedDateTime + file.getOriginalFilename());
		    Gson gson = new Gson();
		    String jsonResponse = gson.toJson(response);
		    
		    return ResponseEntity.ok().body(jsonResponse);
//		    return ResponseEntity.ok().headers(headers).body(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
            CKEditorResponse response = new CKEditorResponse();
            response.setUploaded(false);
            response.setError("There was an error uploading the image.");
		    Gson gson = new Gson();
		    String jsonResponse = gson.toJson(response);
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.set("X-Frame-Options", "SAMEORIGIN");
            // 返回錯誤消息
//		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(jsonResponse);
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}

