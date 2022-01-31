package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    final ICategoryRepository repository;
    List<Category> categories;
    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }


    private boolean categoryExistent(String name) {
        return categories.stream().anyMatch(category -> category.getName().equals(name));
    }

    public List<Category> getCategories(MongoUser user) {
        LOG.debug("Loading user categories from: " + user.getUsername());
        return repository.findAllByUserID(user.getId());
    }

    public List<Category> addCategory(MongoUser user, String categoryName) throws CategoryAlreadyExistException {
        LOG.debug("Adding category " + categoryName + " for user: " + user.getUsername());
        categories = repository.findAllByUserID(user.getId());

        if (!categoryExistent(categoryName)) {
            Category category = Category.builder()
                    .id(UUID.randomUUID().toString())
                    .userID(user.getId())
                    .name(categoryName)
                    .paymentSum(0)
                    .build();
            repository.save(category);
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
        Category category = repository.findByUserIDAndId(user.getId(), categoryID);

        if (category != null) {
            if (!repository.existsByUserIDAndName(user.getId(), name)){
                category.setName(name);
                repository.save(category);
                LOG.debug("Renamed category with ID: " + categoryID);
            } else {
                LOG.debug("A repository with this name already exists");
                throw new CategoryAlreadyExistException("A category with this name already exists: " + name);
            }
        } else {
            LOG.debug("Category does not exist");
            throw new CategoryDoesNotExistException("No category with ID: " + categoryID);
        }
        return repository.findAllByUserID(user.getId());
    }

    // TODO Remove associated payments
    public List<Category> deleteCategory(MongoUser user, String categoryID) {
        LOG.debug("Deleting category with ID: " + categoryID + " from user: " + user.getUsername());
        repository.deleteById(categoryID);
        return repository.findAllByUserID(user.getId());
    }


}
