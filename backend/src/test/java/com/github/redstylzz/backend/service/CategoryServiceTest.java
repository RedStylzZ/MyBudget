package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private final ICategoryRepository repository = mock(ICategoryRepository.class);
    private final CategoryService underTest = new CategoryService(repository);

    @BeforeEach
    void beforeEach() {
        when(repository.findAllByUserID(anyString())).thenReturn(new ArrayList<>());
    }

    @Test
    void getCategories() {
        underTest.getCategories(TestDataProvider.testUser());

        verify(repository).findAllByUserID(anyString());
    }

    @Test
    void shouldThrowExceptionIfCategoryExists() {
        MongoUser user = TestDataProvider.testUser();
        Category category = TestDataProvider.testCategory();
        String categoryName = category.getName();
        when(repository.findAllByUserID(anyString())).thenReturn(List.of(category));

        assertThrows(CategoryAlreadyExistException.class, () ->
                underTest.addCategory(user, categoryName));
    }

    @Test
    void shouldAddCategoryIfNotExistentAndReturnCategories() {
        when(repository.save(any(Category.class))).thenReturn(null);

        underTest.addCategory(TestDataProvider.testUser(), TestDataProvider.testCategory().getName());

        verify(repository).save(any(Category.class));
    }

    @Test
    void renameCategory() {

    }

    @Test
    void deleteCategory() {
    }
}