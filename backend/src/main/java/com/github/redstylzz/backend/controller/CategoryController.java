package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.exception.CategoryAlreadyExistException;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.CategoryDTO;
import com.github.redstylzz.backend.model.dto.CategoryNameDTO;
import com.github.redstylzz.backend.service.CategoryService;
import com.github.redstylzz.backend.service.MongoUserService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private static final Log LOG = LogFactory.getLog(CategoryController.class);
    private final CategoryService service;
    private final MongoUserService userService;

    @GetMapping
    public List<CategoryDTO> getCategories(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.getCategories(user);
    }

    @PostMapping
    public List<CategoryDTO> addCategory(UsernamePasswordAuthenticationToken principal, @RequestBody CategoryNameDTO dto) throws ResponseStatusException {
        String name = dto.getCategoryName();
        if (name == null || name.isBlank()) {
            LOG.warn("Name is null or blank");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No name given");
        }

        MongoUser user = MongoUser.getUser(principal, LOG);
        try {
            return service.addCategory(user, name);
        } catch (CategoryAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping
    public List<CategoryDTO> renameCategory(UsernamePasswordAuthenticationToken principal, @RequestBody CategoryDTO dto) throws ResponseStatusException {
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
    public List<CategoryDTO> deleteCategory(UsernamePasswordAuthenticationToken principal, @RequestParam String categoryID) throws ResponseStatusException {
        if (categoryID == null || categoryID.isBlank()) {
            LOG.warn("ID is null or blank");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        MongoUser user = userService.getUserByPrincipal(principal);
        return service.deleteCategory(user, categoryID);
    }


}
