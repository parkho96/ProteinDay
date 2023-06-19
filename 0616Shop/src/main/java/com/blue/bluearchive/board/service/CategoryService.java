package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.CategoryDto;
import com.blue.bluearchive.board.entity.Category;
import com.blue.bluearchive.board.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
    }
    public List<CategoryDto> getAllCategory() {
        List<Category> CategoryEntities = categoryRepository.findAll();
        List<CategoryDto> CategoryDtos = new ArrayList<>();
        for (Category category : CategoryEntities) {
            CategoryDtos.add(modelMapper.map(category, CategoryDto.class));
        }
        return CategoryDtos;
    }




}