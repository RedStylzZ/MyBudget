package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    private final ICategoryRepository categoryRepository;
    private final IPaymentRepository paymentRepo;
    List<Category> categories;

    public CategoryService(ICategoryRepository categoryRepository, IPaymentRepository paymentRepo) {
        this.categoryRepository = categoryRepository;
        this.paymentRepo = paymentRepo;
    }


    private boolean categoryExistent(String name) {
        return categories.stream().anyMatch(category -> category.getCategoryName().equals(name));
    }

    public List<Category> getCategories(MongoUser user) {
        LOG.debug("Loading user categories from: " + user.getUsername());
        return categoryRepository.findAllByUserID(user.getId());
    }

    public List<Category> addCategory(MongoUser user, String categoryName) throws CategoryAlreadyExistException {
        LOG.debug("Adding category " + categoryName + " for user: " + user.getUsername());
        categories = categoryRepository.findAllByUserID(user.getId());

        if (!categoryExistent(categoryName)) {
            Category category = Category.builder()
                    .categoryID(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .categoryName(categoryName)
                    .paymentSum(new BigDecimal("0"))
                    .build();
            categoryRepository.save(category);
            categories.add(category);
            LOG.debug("Added category: " + categoryName);
        } else {
            LOG.debug("Category already existent");
            throw new CategoryAlreadyExistException("A category with this name already exists: " + categoryName);
        }
        return categories;
    }

    public List<Category> renameCategory(MongoUser user, String categoryID, String name) throws CategoryAlreadyExistException, CategoryDoesNotExistException {
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
        return categoryRepository.findAllByUserID(user.getId());
    }

    public List<Category> deleteCategory(MongoUser user, String categoryID) {
        LOG.debug("Deleting category with ID: " + categoryID + " from user: " + user.getUsername());
        categoryRepository.deleteByCategoryID(categoryID);
        paymentRepo.deleteAllByUserIDAndCategoryID(user.getId(), categoryID);
        return categoryRepository.findAllByUserID(user.getId());
    }

    public void setCategorySum(String userID, String categoryID, BigDecimal paymentSum) {
        LOG.debug("Calculating payment sum for category: " + categoryID);
        if (paymentSum != null) {
            Category category = categoryRepository.findByUserIDAndCategoryID(userID, categoryID);
            category.setPaymentSum(paymentSum);
            categoryRepository.save(category);
        }
    }


}
