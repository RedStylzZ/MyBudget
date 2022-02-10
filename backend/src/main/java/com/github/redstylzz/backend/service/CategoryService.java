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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    private final ICategoryRepository categoryRepository;
    private final IPaymentRepository paymentRepo;

    private List<CategoryDTO> getAllCategoriesAsDTO(String userID) {
        return categoryRepository.findAllByUserID(userID)
                .stream()
                .map(CategoryDTO::mapCategoryToDTO)
                .toList();
    }

    public List<CategoryDTO> getCategories(MongoUser user) {
        LOG.debug("Loading user categories from: " + user.getUsername());
        return getAllCategoriesAsDTO(user.getId());
    }

    public List<CategoryDTO> addCategory(MongoUser user, String categoryName) throws CategoryAlreadyExistException {
        LOG.debug("Adding category " + categoryName + " for user: " + user.getUsername());

        if (!categoryRepository.existsByUserIDAndCategoryName(user.getId(), categoryName)) {
            Category category = Category.builder()
                    .categoryID(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .categoryName(categoryName)
                    .build();
            categoryRepository.save(category);
            LOG.debug("Added category: " + categoryName);
        } else {
            LOG.debug("Category already existent");
            throw new CategoryAlreadyExistException("A category with this name already exists: " + categoryName);
        }
        return getAllCategoriesAsDTO(user.getId());
    }

    public List<CategoryDTO> renameCategory(MongoUser user, String categoryID, String name) throws CategoryAlreadyExistException, CategoryDoesNotExistException {
        LOG.debug("Renaming category: " + categoryID + " from user: " + user.getUsername());
        LOG.debug("Loading category with ID: " + categoryID);
        Category category = categoryRepository.findByUserIDAndCategoryID(user.getId(), categoryID);

        if (category != null) {
            if (!categoryRepository.existsByUserIDAndCategoryName(user.getId(), name)) {
                category.setCategoryName(name);
                categoryRepository.save(category);
                LOG.debug("Renamed category with ID: " + categoryID);
            } else {
                LOG.debug("A repository with this name already exists");
                throw new CategoryAlreadyExistException("A category with this name already exists: " + name);
            }
        } else {
            LOG.debug("Category does not exist");
            throw new CategoryDoesNotExistException("No category with ID: " + categoryID);
        }
        return getAllCategoriesAsDTO(user.getId());
    }

    public List<CategoryDTO> deleteCategory(MongoUser user, String categoryID) {
        LOG.debug("Deleting category with ID: " + categoryID + " from user: " + user.getUsername());
        categoryRepository.deleteByCategoryID(categoryID);
        paymentRepo.deleteAllByUserIDAndCategoryID(user.getId(), categoryID);
        return getAllCategoriesAsDTO(user.getId());
    }


}
