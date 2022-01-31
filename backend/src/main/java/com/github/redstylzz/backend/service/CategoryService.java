package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    final ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }


    public List<Category> getCategories(MongoUser user) {
        LOG.debug("Loading user categories from: " + user.getUsername());
        return repository.findAllByUserID(user.getId());
    }



}
