package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private final ICategoryRepository repository = mock(ICategoryRepository.class);
    private final CategoryService underTest = new CategoryService(repository);

    @Test
    void shouldFetchCategories() {
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
        MongoUser user = TestDataProvider.testUser();
        UUID randUUID = mockUUID();
        Category category = TestDataProvider.testCategory();
        category.setId(randUUID.toString());
        String categoryName = category.getName();
        when(repository.save(any(Category.class))).thenReturn(null);

        List<Category> wantedList = underTest.addCategory(user, categoryName);

        verify(repository).save(any(Category.class));
        assertEquals(wantedList, List.of(category));
    }

    @Test
    void shouldThrowExceptionIfCategoryNotExistsWithExplicitIdOnRename() {
        MongoUser user = TestDataProvider.testUser();

        assertThrows(CategoryDoesNotExistException.class, () ->
                underTest.renameCategory(user, "", ""));
    }

    @Test
    void shouldThrowExceptionIfCategoryExistsWithNameOnRename() {
        MongoUser user = TestDataProvider.testUser();
        when(repository.findByUserIDAndId(anyString(), anyString())).thenReturn(TestDataProvider.testCategory());
        when(repository.existsByUserIDAndName(anyString(), anyString())).thenReturn(true);

        assertThrows(CategoryAlreadyExistException.class, () ->
                underTest.renameCategory(user, "", ""));
    }

    @Test
    void shouldReturnListOnSuccessfulRename() {
        MongoUser user = TestDataProvider.testUser();
        when(repository.findByUserIDAndId(anyString(), anyString())).thenReturn(TestDataProvider.testCategory());
        when(repository.existsByUserIDAndName(anyString(), anyString())).thenReturn(false);

        underTest.renameCategory(user, "", "");

        verify(repository).save(any(Category.class));
        verify(repository).findAllByUserID(anyString());

    }

    @Test
    void shouldReturnListOnSuccessfulDelete() {
        MongoUser user = TestDataProvider.testUser();

        underTest.deleteCategory(user, "");

        verify(repository).deleteById(anyString());
        verify(repository).findAllByUserID(anyString());
    }

    UUID mockUUID() {
        UUID randUUID = UUID.randomUUID();
        MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
        uuidMock.when(UUID::randomUUID).thenReturn(randUUID);
        return randUUID;
    }
}
