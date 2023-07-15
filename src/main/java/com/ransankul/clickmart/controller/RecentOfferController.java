package com.ransankul.clickmart.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.model.RecentOffer;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.service.RecentOfferService;

@RestController
@RequestMapping("/recentoffer")
public class RecentOfferController {
	
	@Autowired
	private RecentOfferService service;
	
    @Value("${project.image}")
    private String path;
	
	
    @GetMapping("/")
    public List<RecentOffer> lastRecentOffer() {
    	
    	return this.service.recentOffer();
    	
    }
    
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> recentOfferImage(@PathVariable String imageName) throws MalformedURLException {
    	String folderPath = path+File.separator+"recentOfferImages";
        Path imagePath = Paths.get(folderPath).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        
        if (imageResource.exists() && imageResource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 
        
            return ResponseEntity.ok().headers(headers).body(imageResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    	
    }
	
	
	

}
