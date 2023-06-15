package com.ransankul.clickmart.service;

import java.util.List;


import com.ransankul.clickmart.model.Category;

public interface CategoryService {

    //getAllCategory
    public List<Category> getAllCategory();

    //getCategoryByID
    public Category getCategoryByID();

    //searchCategory
    public List<Category> searchCategory(String searchCategoryName); 


}
