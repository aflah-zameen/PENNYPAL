package com.application.pennypal.interfaces.rest.controllers;

import com.application.pennypal.application.output.paged.PagedResultOutput;
import com.application.pennypal.application.output.user.UserFiltersOutput;
import com.application.pennypal.application.usecases.admin.FetchUsers;
import com.application.pennypal.application.usecases.category.*;
import com.application.pennypal.application.usecases.user.GetUser;
import com.application.pennypal.domain.entity.Category;
import com.application.pennypal.domain.entity.User;
import com.application.pennypal.application.port.UserRepositoryPort;
import com.application.pennypal.domain.valueObject.CategoryType;
import com.application.pennypal.domain.valueObject.Roles;
import com.application.pennypal.domain.valueObject.UserDomainDTO;
import com.application.pennypal.interfaces.rest.dtos.ApiResponse;
import com.application.pennypal.interfaces.rest.dtos.catgeory.CategoryRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private  final UserRepositoryPort userRepository;
    private final FetchUsers fetchUsers;
    private final GetUser getUser;
    private final CreateCategory createCategory;
    private final GetCategories getCategories;
    private final UpdateCategory updateCategory;
    private final ToggleCategoryStatus toggleCategoryStatus;
    private final DeleteCategory deleteCategory;

    //user management
    @GetMapping("fetch-filtered-users")
    public ResponseEntity<ApiResponse<PagedResultOutput<User>>> getFilteredUsers(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "status",required = false) Boolean status,
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "role",required = false) String role,
            @RequestParam(name = "joinedAfter",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime joinedAfter,
            @RequestParam(name = "joinedBefore",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime joinedBefore
            ){
        Roles enumRole = null;
        if(role != null){
            enumRole= switch(role){
                case "USER" -> Roles.USER;
                case "ADMIN" -> Roles.ADMIN;
                case "SUPER_ADMIN" -> Roles.SUPER_ADMIN;
                default -> null;
            };
        }
        PagedResultOutput<User> userPagedResultOutput = fetchUsers.execute(new UserFiltersOutput(
                enumRole,joinedBefore,joinedAfter,status
        ),page,size,keyword
                );
        return ResponseEntity.ok(new ApiResponse<>(true, userPagedResultOutput,"Users details fetched successfully"));
    }

    @PatchMapping("/users/{email}/toggle-active")
    public ResponseEntity<User> toggleUserActive(
            @PathVariable String email,
            @RequestBody Map<String, Boolean> request
    ) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(request.get("active"));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    //category management
    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse<Category>> addNewCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, HttpServletRequest httpServletRequest){
            UserDomainDTO user= getUser.get(extractTokenFromCookie(httpServletRequest,"accessToken"));
            Category category = Category.builder()
                    .name(categoryRequestDTO.name())
                    .description(categoryRequestDTO.description())
                    .sortOrder(categoryRequestDTO.sortOrder())
                    .usageTypes(Arrays.stream(categoryRequestDTO.usageTypes()).map(String::toUpperCase).map(CategoryType::valueOf).toList())
                    .usageCount(categoryRequestDTO.usageCount())
                    .color(categoryRequestDTO.color())
                    .icon(categoryRequestDTO.icon())
                    .isDefault(categoryRequestDTO.isDefault())
                    .isActive(categoryRequestDTO.active())
                    .build();
            category = createCategory.execute(category, user.id());
            return ResponseEntity.ok(new ApiResponse<>(true,category,"Successfully category created"));
    }

    @GetMapping("/get-categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(){
        List<Category> categories = getCategories.execute();
        return ResponseEntity.ok(new ApiResponse<>(true,categories,"All categories fetched successfully"));
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @RequestBody CategoryRequestDTO categoryRequestDTO,
            @PathVariable("id") Long categoryId,HttpServletRequest request){
        String token = extractTokenFromCookie(request,"accessToken");
        UserDomainDTO user = getUser.get(token);
        Category category = Category.builder()
                .name(categoryRequestDTO.name())
                .description(categoryRequestDTO.description())
                .sortOrder(categoryRequestDTO.sortOrder())
                .usageTypes(Arrays.stream(categoryRequestDTO.usageTypes()).map(String::toUpperCase).map(CategoryType::valueOf).toList())
                .usageCount(categoryRequestDTO.usageCount())
                .color(categoryRequestDTO.color())
                .icon(categoryRequestDTO.icon())
                .isDefault(categoryRequestDTO.isDefault())
                .isActive(categoryRequestDTO.active())
                .build();
         category = updateCategory.update(category,categoryId, user.id());
         return ResponseEntity.ok(new ApiResponse<>(true,category,"Updated successfully"));
    }

    @PatchMapping("/toggle-category-status/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable("id") Long categoryId,HttpServletRequest request){
            String token = extractTokenFromCookie(request,"accessToken");
            UserDomainDTO userDomainDTO = getUser.get(token);
            Category category = toggleCategoryStatus.toggle(categoryId,userDomainDTO.id());
            return ResponseEntity.ok(new ApiResponse<>(true, category,"Updated category status"));
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable("id") Long categoryId){
        deleteCategory.delete(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(true,null,"Deleted successfully"));
    }


    private String extractTokenFromCookie(HttpServletRequest request, String tokenName) {
        if(request.getCookies() != null){
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> tokenName.equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }
}
