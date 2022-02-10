package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private final ICategoryRepository repository = mock(ICategoryRepository.class);
    private final IPaymentRepository paymentRepo = mock(IPaymentRepository.class);
    private final PaymentService paymentService = mock(PaymentService.class);
    private final CategoryService underTest = new CategoryService(repository, paymentRepo, paymentService);
    private static final String UUID_STRING = "06f1eb01-cdaf-46e4-a3c8-eff2e4b300dd";
    private static final UUID uuid = UUID.fromString(UUID_STRING);
    private static MockedStatic<UUID> uuidMock;


    @BeforeAll
    static void init() {
        uuidMock = mockStatic(UUID.class);
        uuidMock.when(UUID::randomUUID).thenReturn(uuid);
    }

    @AfterAll
    static void end() {
        uuidMock.close();
    }

    @Test
    void shouldFetchCategories() {
        underTest.getCategories(TestDataProvider.testUser());

        verify(repository).findAllByUserID(anyString());
    }

    @Test
    void shouldThrowExceptionIfCategoryExists() {
        MongoUser user = TestDataProvider.testUser();
        Category category = TestDataProvider.testCategory();
        String categoryName = category.getCategoryName();
        when(repository.existsByUserIDAndCategoryName(anyString(), anyString())).thenReturn(true);

        assertThrows(CategoryAlreadyExistException.class, () ->
                underTest.addCategory(user, categoryName));
    }

    @Test
    void shouldAddCategoryIfNotExistentAndReturnCategories() {
        MongoUser user = TestDataProvider.testUser();
        Category category = TestDataProvider.testCategory();
        category.setCategoryID(UUID_STRING);
        String categoryName = category.getCategoryName();
        when(repository.save(any(Category.class))).thenReturn(null);
        when(repository.findAllByUserID(anyString())).thenReturn(List.of(category));
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
        when(repository.findByUserIDAndCategoryID(anyString(), anyString())).thenReturn(TestDataProvider.testCategory());
        when(repository.existsByUserIDAndCategoryName(anyString(), anyString())).thenReturn(true);

        assertThrows(CategoryAlreadyExistException.class, () ->
                underTest.renameCategory(user, "", ""));
    }

    @Test
    void shouldReturnListOnSuccessfulRename() {
        MongoUser user = TestDataProvider.testUser();
        when(repository.findByUserIDAndCategoryID(anyString(), anyString())).thenReturn(TestDataProvider.testCategory());
        when(repository.existsByUserIDAndCategoryName(anyString(), anyString())).thenReturn(false);

        List<CategoryDTO> wantedList = underTest.renameCategory(user, "", "");

        verify(repository).save(any(Category.class));
        verify(repository).findAllByUserID(anyString());
        assertEquals(List.of(), wantedList);
    }

    @Test
    void shouldReturnListOnSuccessfulDelete() {
        MongoUser user = TestDataProvider.testUser();

        List<CategoryDTO> wantedList = underTest.deleteCategory(user, "");

        verify(repository).deleteByCategoryID(anyString());
        verify(paymentRepo).deleteAllByUserIDAndCategoryID(anyString(), anyString());
        verify(repository).findAllByUserID(anyString());
        assertEquals(List.of(), wantedList);
    }
}
