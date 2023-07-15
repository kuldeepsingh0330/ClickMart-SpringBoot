package com.ransankul.clickmart.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Value("${project.image}")
    private String path;

    @GetMapping("/")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryByID(@PathVariable int categoryId) {
        Category category = categoryService.getCategoryByID(categoryId);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            throw new ResourceNotFoundException("No category found with this ID" + categoryId);
        }
    }

    @GetMapping("/search")
    public List<Category> searchCategory(@RequestParam("name") String searchCategoryName) {
        List<Category> list =  categoryService.searchCategory(searchCategoryName);
        if(list.size()>0) return list;
        else throw new ResourceNotFoundException("No category found with this name" + searchCategoryName);
    }
    
    @GetMapping("/category_image/{categoryImage}")
    public ResponseEntity<Resource> serveImage(@PathVariable String categoryImage) throws IOException {
        String folderPath = path+File.separator+"categoryImage";
        Path imagePath = Paths.get(folderPath).resolve(categoryImage);
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
