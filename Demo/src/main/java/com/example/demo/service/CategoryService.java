package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getCategorie()
    {
        return categoryRepository.findAll();
    }
    public Category create( Category category)
    {
        if(categoryRepository.existsByName(category.getName()))
            throw new RuntimeException("Ngành nghề nãy đã tồn tại");
        return  categoryRepository.save(category);
    }

    public Category getCategoryById(Long id)
    {
        return  categoryRepository.findById(id).orElse(null);
    }
    public Category updateCategory(Long id, Category category) {


        if(categoryRepository.existsByName(category.getName())) {
            Category current = getCategoryById(id);
                throw new RuntimeException("Ngành nghề này đã tồn tại");
        }
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }

}
