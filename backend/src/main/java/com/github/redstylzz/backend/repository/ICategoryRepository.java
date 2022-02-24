package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, String> {

    List<Category> findAllByUserId(String userId);

    Category findByUserIdAndCategoryId(String userId, String categoryId);

    void deleteByCategoryId(String categoryId);

    boolean existsByUserIdAndCategoryName(String userId, String categoryName);

    boolean existsByUserIdAndCategoryId(String userId, String categoryId);

}
