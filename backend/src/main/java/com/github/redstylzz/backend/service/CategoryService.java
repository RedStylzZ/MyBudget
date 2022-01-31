package com.github.redstylzz.backend.service;

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


    public List<Category> getCategories(MongoUser user) {
        LOG.debug("Loading user categories from: " + user.getUsername());
        return repository.findAllByUserID(user.getId());
    }

    private boolean categoryExistent(String name) {
        return categories.stream().anyMatch(category -> category.getName().equals(name));
    }

    public List<Category> addCategory(MongoUser user, String categoryName) {
        LOG.debug("Adding Category " + categoryName + " for user: " + user.getUsername());
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
        } else {
            LOG.debug("Category already existent");
        }
        return categories;
    }



}
