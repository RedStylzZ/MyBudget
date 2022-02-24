package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import com.github.redstylzz.backend.repository.IPaymentSeriesRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private final ICategoryRepository repository = mock(ICategoryRepository.class);
    private final IPaymentRepository paymentRepo = mock(IPaymentRepository.class);
    private final IPaymentSeriesRepository paymentSeriesRepository = mock(IPaymentSeriesRepository.class);
    private final PaymentService paymentService = mock(PaymentService.class);
    private final CategoryService underTest = new CategoryService(repository, paymentRepo, paymentSeriesRepository, paymentService);

    @Test
    void shouldFetchCategories() {
        underTest.getCategories(TestDataProvider.testUser());

        verify(repository).findAllByUserId(anyString());
    }

    @Test
    void shouldThrowExceptionIfCategoryExists() {
        MongoUser user = TestDataProvider.testUser();
        Category category = TestDataProvider.testCategory();
        String categoryName = category.getCategoryName();
        when(repository.existsByUserIdAndCategoryName(anyString(), anyString())).thenReturn(true);

        assertThrows(CategoryAlreadyExistException.class, () ->
                underTest.addCategory(user, categoryName));
    }

    @Test
    void shouldAddCategoryIfNotExistentAndReturnCategories() {
        MongoUser user = TestDataProvider.testUser();
        Category category = TestDataProvider.testCategory();
        String categoryName = category.getCategoryName();
        when(repository.save(any(Category.class))).thenReturn(null);
        when(repository.findAllByUserId(anyString())).thenReturn(List.of(category));
        List<CategoryDTO> wantedList = underTest.addCategory(user, categoryName);

        verify(repository).save(any(Category.class));
        assertEquals(List.of(CategoryDTO.mapCategoryToDTO(category)), wantedList);
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
        when(repository.findByUserIdAndCategoryId(anyString(), anyString())).thenReturn(TestDataProvider.testCategory());
        when(repository.existsByUserIdAndCategoryName(anyString(), anyString())).thenReturn(true);

        assertThrows(CategoryAlreadyExistException.class, () ->
                underTest.renameCategory(user, "", ""));
    }

    @Test
    void shouldReturnListOnSuccessfulRename() {
        MongoUser user = TestDataProvider.testUser();
        when(repository.findByUserIdAndCategoryId(anyString(), anyString())).thenReturn(TestDataProvider.testCategory());
        when(repository.existsByUserIdAndCategoryName(anyString(), anyString())).thenReturn(false);

        List<CategoryDTO> wantedList = underTest.renameCategory(user, "", "");

        verify(repository).save(any(Category.class));
        verify(repository).findAllByUserId(anyString());
        assertEquals(List.of(), wantedList);
    }

    @Test
    void shouldReturnListOnSuccessfulDelete() {
        MongoUser user = TestDataProvider.testUser();

        List<CategoryDTO> wantedList = underTest.deleteCategory(user, "");

        verify(repository).deleteByCategoryId(anyString());
        verify(paymentRepo).deleteAllByUserIdAndCategoryId(anyString(), anyString());
        verify(repository).findAllByUserId(anyString());
        assertEquals(List.of(), wantedList);
    }
}
