package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.model.dto.CategoryNameDTO;
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
    public List<CategoryDTO> getCategories(Principal principal) {
        MongoUser user = getUser(principal);
        return service.getCategories(user);
    }

    @PutMapping
    public List<CategoryDTO> addCategory(Principal principal, @RequestBody CategoryNameDTO dto) throws ResponseStatusException {
        String name = dto.getCategoryName();
        if (name == null || name.isBlank()) {
            LOG.warn("Name is null or blank");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No name given");
        }

        MongoUser user = getUser(principal);
        try {
            return service.addCategory(user, name);
        } catch (CategoryAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public List<CategoryDTO> renameCategory(Principal principal, @RequestBody CategoryDTO dto) throws ResponseStatusException {
        String id = dto.getCategoryID();
        String name = dto.getCategoryName();
        if ((id == null || id.isBlank()) || (name == null || name.isBlank())) {
            LOG.warn("ID or name is null or blank");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        MongoUser user = userService.getUserByPrincipal(principal);
        return service.renameCategory(user, id, name);
    }

    @DeleteMapping
    public List<CategoryDTO> deleteCategory(Principal principal, @RequestParam String categoryID) throws ResponseStatusException {
        if (categoryID == null || categoryID.isBlank()) {
            LOG.warn("ID is null or blank");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        MongoUser user = userService.getUserByPrincipal(principal);
        return service.deleteCategory(user, categoryID);
    }


}
