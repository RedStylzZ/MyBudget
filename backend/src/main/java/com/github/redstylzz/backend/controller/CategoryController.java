package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.service.CategoryService;
import com.github.redstylzz.backend.service.MongoUserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final Log LOG = LogFactory.getLog(CategoryController.class);
    private final CategoryService service;
    private final MongoUserService userService;

    public CategoryController(CategoryService service, MongoUserService userService) {
        this.service = service;
        this.userService = userService;
    }

    private MongoUser getUser(Principal principal) throws UsernameNotFoundException {
        if (principal != null) {
            return userService.loadUserByUsername(principal.getName());
        }
        LOG.debug("Principal is null");
        throw new UsernameNotFoundException("Principal is null");
    }

    @GetMapping
    public List<Category> getCategories(Principal principal) {
        try {
            MongoUser user = getUser(principal);
            return service.getCategories(user);
        } catch (UsernameNotFoundException e) {
            return List.of();
        }
    }

    @PutMapping
    public List<Category> addCategory(Principal principal, @RequestBody CategoryDTO dto) {
        String name = dto.getCategoryName();
        if (name == null || name.isBlank()) return List.of();

        try {
            MongoUser user = getUser(principal);
            return service.addCategory(user, name);
        } catch (Exception e) {
            return List.of();
        }
    }

    @PostMapping
    public List<Category> renameCategory(Principal principal, @RequestBody CategoryDTO dto) {
        String id = dto.getCategoryID();
        String name = dto.getCategoryName();
        if ((id == null || id.isBlank()) || (name == null || name.isBlank())) return List.of();

        try {
            MongoUser user = getUser(principal);
            return service.renameCategory(user, id, name);
        } catch (Exception e) {
            return List.of();
        }
    }

    @DeleteMapping
    List<Category> deleteCategory(Principal principal, @RequestBody CategoryDTO dto) {
        String id = dto.getCategoryID();
        if (id == null || id.isBlank()) return List.of();

        try {
            MongoUser user = getUser(principal);
            return service.deleteCategory(user, id);
        } catch (Exception e) {
            return List.of();
        }
    }


}
