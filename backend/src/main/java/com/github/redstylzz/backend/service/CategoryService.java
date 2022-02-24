package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    private final ICategoryRepository categoryRepository;
    private final IPaymentRepository paymentRepo;
    private final PaymentService paymentService;

    private List<CategoryDTO> getAllCategoriesAsDTO(String userId) {
        List<CategoryDTO> dto = categoryRepository.findAllByUserId(userId)
                .stream()
                .map(CategoryDTO::mapCategoryToDTO)
                .toList();
        dto.forEach(categoryDTO -> categoryDTO.setPaymentSum(paymentService.calculatePaymentSum(userId, categoryDTO.getCategoryId())));
        return dto;
    }

    public List<CategoryDTO> getCategories(MongoUser user) {
        LOG.info("Loading user categories");
        return getAllCategoriesAsDTO(user.getId());
    }

    public List<CategoryDTO> addCategory(MongoUser user, String categoryName) throws CategoryAlreadyExistException {
        LOG.info("Adding category");

        if (!categoryRepository.existsByUserIdAndCategoryName(user.getId(), categoryName)) {
            Category category = Category.builder()
                    .userId(user.getId())
                    .categoryName(categoryName)
                    .saveDate(LocalDateTime.now())
                    .build();
            categoryRepository.save(category);
            LOG.info("Added category");
        } else {
            LOG.warn("Category already existent");
            throw new CategoryAlreadyExistException("A category with this name already exists");
        }
        return getAllCategoriesAsDTO(user.getId());
    }

    public List<CategoryDTO> renameCategory(MongoUser user, String categoryId, String name) throws CategoryAlreadyExistException, CategoryDoesNotExistException {
        LOG.info("Renaming category");
        Category category = categoryRepository.findByUserIdAndCategoryId(user.getId(), categoryId);

        if (category != null) {
            if (!categoryRepository.existsByUserIdAndCategoryName(user.getId(), name)) {
                category.setCategoryName(name);
                category.setSaveDate(LocalDateTime.now());
                categoryRepository.save(category);
                LOG.info("Renamed category with Id");
            } else {
                LOG.warn("A category with this name already exists");
                throw new CategoryAlreadyExistException("A category with this name already exists");
            }
        } else {
            LOG.warn("Category does not exist");
            throw new CategoryDoesNotExistException("No category with this Id");
        }
        return getAllCategoriesAsDTO(user.getId());
    }

    public List<CategoryDTO> deleteCategory(MongoUser user, String categoryId) {
        LOG.debug("Deleting category");
        categoryRepository.deleteByCategoryId(categoryId);
        paymentRepo.deleteAllByUserIdAndCategoryId(user.getId(), categoryId);
        return getAllCategoriesAsDTO(user.getId());
    }


}
