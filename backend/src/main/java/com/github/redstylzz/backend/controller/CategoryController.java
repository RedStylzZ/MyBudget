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
    private static final String ID_NOT_GIVEN = "Id is not given";
    private static final String NAME_NOT_GIVEN = "Name is not given";
    private final CategoryService service;
    private final MongoUserService userService;

    private MongoUser getUser(UsernamePasswordAuthenticationToken principal) throws ResponseStatusException {
        try {
            return (MongoUser) principal.getPrincipal();
        } catch (Exception e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @GetMapping
    public List<CategoryDTO> getCategories(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal);
        return service.getCategories(user);
    }

    @PostMapping
    public List<CategoryDTO> addCategory(UsernamePasswordAuthenticationToken principal, @RequestBody CategoryNameDTO dto) throws ResponseStatusException {
        String name = dto.getCategoryName();
        if (name == null || name.isBlank()) {
            LOG.warn(NAME_NOT_GIVEN);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NAME_NOT_GIVEN);
        }

        MongoUser user = getUser(principal);
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
        if (id == null || id.isBlank()) {
            LOG.warn(ID_NOT_GIVEN);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_NOT_GIVEN);
        }
        if (name == null || name.isBlank()) {
            LOG.warn(NAME_NOT_GIVEN);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NAME_NOT_GIVEN);
        }
        MongoUser user = userService.getUserByPrincipal(principal);
        return service.renameCategory(user, id, name);
    }

    @DeleteMapping
    public List<CategoryDTO> deleteCategory(UsernamePasswordAuthenticationToken principal, @RequestParam String categoryID) throws ResponseStatusException {
        if (categoryID == null || categoryID.isBlank()) {
            LOG.warn(ID_NOT_GIVEN);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_NOT_GIVEN);
        }

        MongoUser user = userService.getUserByPrincipal(principal);
        return service.deleteCategory(user, categoryID);
    }


}
