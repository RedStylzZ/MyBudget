package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.service.CategoryService;
import com.github.redstylzz.backend.service.MongoUserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    private MongoUser getUser(Principal principal) throws ResponseStatusException {
        try {
            return userService.getUserByPrincipal(principal);
        } catch (UsernameNotFoundException e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @GetMapping
    public List<Category> getCategories(Principal principal) {
        MongoUser user = getUser(principal);
        return service.getCategories(user);
    }

    @PutMapping
    public List<Category> addCategory(Principal principal, @RequestBody CategoryDTO dto) {
        String name = dto.getCategoryName();
        if (name == null || name.isBlank()) return List.of();

        MongoUser user = getUser(principal);
        return service.addCategory(user, name);
    }

    @PostMapping
    public List<Category> renameCategory(Principal principal, @RequestBody CategoryDTO dto) {
        String id = dto.getCategoryID();
        String name = dto.getCategoryName();
        if ((id == null || id.isBlank()) || (name == null || name.isBlank())) return List.of();

        MongoUser user = userService.getUserByPrincipal(principal);
        return service.renameCategory(user, id, name);
    }

    @DeleteMapping
    List<Category> deleteCategory(Principal principal, @RequestBody CategoryDTO dto) {
        String id = dto.getCategoryID();
        if (id == null || id.isBlank()) return List.of();

        MongoUser user = userService.getUserByPrincipal(principal);
        return service.deleteCategory(user, id);
    }


}
