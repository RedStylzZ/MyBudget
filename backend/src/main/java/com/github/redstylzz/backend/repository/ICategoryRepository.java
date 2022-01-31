package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, String> {

    List<Category> findAllByUserID(String userID);

}
