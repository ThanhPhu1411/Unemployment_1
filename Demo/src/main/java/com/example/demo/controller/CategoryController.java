package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }
    @PostMapping("/createCategory")
    public Category createCategory (@RequestBody Category category)
    {
        return categoryService.create(category);
    }
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") Long id)
    {
        return categoryService.getCategoryById(id);
    }
    @GetMapping("/list")
    public List<Category> getCategorie()
    {
        return  categoryService.getCategorie();
    }
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable("id") Long id, @RequestBody Category category)
    {
        return categoryService.updateCategory(id,category);
    }
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Long id)
    {
        categoryService.deleteCategory(id);
        return "Ngành nghề đã được xóa";
    }

}
