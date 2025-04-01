package com.example.projectc1023i1.controller.category;

import com.example.projectc1023i1.Dto.SubCategoriesDTO;
import com.example.projectc1023i1.model.SubCategories;
import com.example.projectc1023i1.respone.errorsValidate.CategoriesErrorsRespone;
import com.example.projectc1023i1.respone.errorsValidate.CreateSubCateErrors;
import com.example.projectc1023i1.service.SubCategoryService;
import com.example.projectc1023i1.service.impl.ICategoriesService;
import com.example.projectc1023i1.service.impl.ISubCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subcategory/")
public class SubCategoryController {
    @Autowired
    private ISubCategoryService subCategoryService;
    @Autowired
    private ICategoriesService categoriesService;
    @PostMapping("create")
    public ResponseEntity<?> createSubCategory(@Valid @RequestBody SubCategoriesDTO categoryDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CreateSubCateErrors combinedErrors = new CreateSubCateErrors();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                String errorMessage = fieldError.getDefaultMessage();
                switch (fieldError.getField()) {
                    case "categoriesId":
                        combinedErrors.setCategoriesId(
                                (combinedErrors.getCategoriesId()!= null ?
                                        combinedErrors.getCategoriesId() + "," : "") + errorMessage);
                        break;
                    case "subCategoryName":
                        combinedErrors.setSubCategoryName(
                                (combinedErrors.getSubCategoryName() != null ?
                                        combinedErrors.getSubCategoryName() + "," : "") + errorMessage
                        );
                        break;
                }
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(combinedErrors);
        }
        SubCategories subCategories = new SubCategories();
        subCategories.setSubCategoryName(categoryDTO.getSubCategoryName());
        subCategories.setCategories(categoriesService.findById(categoryDTO.getCategoriesId()).get());
        subCategoryService.saveSubCategory(subCategories);
        return ResponseEntity.ok().build();
    }
}
