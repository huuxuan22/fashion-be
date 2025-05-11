package com.example.projectc1023i1.controller.users;


import com.example.projectc1023i1.Dto.CollectionDTO;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.request.UpdateUserRequest;
import com.example.projectc1023i1.respone.ApiRespone;
import com.example.projectc1023i1.service.DealService;
import com.example.projectc1023i1.service.ProductService;
import com.example.projectc1023i1.service.UserService;
import com.example.projectc1023i1.service.impl.ICollectionService;
import com.example.projectc1023i1.service.impl.ICouponService;
import com.example.projectc1023i1.service.impl.IDealService;
import com.example.projectc1023i1.service.impl.IProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IDealService dealService;
    @Autowired
    private ICollectionService collectionService;

    @GetMapping("/profile")
    public ResponseEntity<Users> getUserProfileHandler (@AuthenticationPrincipal Users user) throws UserExepion {
        return new ResponseEntity<Users>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{value}")
    public ResponseEntity<List<Users>> getUserHandler (@PathVariable String value) throws UserExepion {
        List<Users> users = userService.searchUsers(value);
        return  new ResponseEntity<List<Users>>(users, HttpStatus.OK);
    }

    @PostMapping("/update-profile")
    public ResponseEntity<ApiRespone> updateProfile (@RequestBody UpdateUserRequest user,
                                                     @AuthenticationPrincipal Users users) throws UserExepion {

        Users userUpdate = userService.updateUser(users.getUserId(),user);
        ApiRespone apiRespone = new ApiRespone("Update Profile User Succes",true);
        return new ResponseEntity<ApiRespone>(apiRespone, HttpStatus.ACCEPTED);
    }

    @GetMapping("/find-product")
    public ResponseEntity<?> findProductHandler(
            @AuthenticationPrincipal Users user,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer sizeId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer subCategoryId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam String sortBy) throws UserExepion {
        Pageable pageable = null;
        if (sortBy.equals("giaTang")) {
            pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        }else if (sortBy.equals("giaGiam")) {
            pageable = PageRequest.of(page, size, Sort.by("price").descending());
        }else {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        }

        Page<Product> result = productService.findProducts(colorId, sizeId, categoryId, subCategoryId, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count-all-product")
    public ResponseEntity<?> getTotalPage(
            @AuthenticationPrincipal Users user,
            @RequestParam (required = false) Integer colorId,
            @RequestParam (required = false) Integer sizeId,
            @RequestParam (required = false) Integer categoryId,
            @RequestParam (required = false) Integer subCategoryId) throws UserExepion {
        return ResponseEntity.ok(productService.getAllTotal(colorId, sizeId, categoryId, subCategoryId));
    }

    @GetMapping("/coupons")
    public ResponseEntity<?> getAllCoupon(@AuthenticationPrincipal Users user,
                                          @RequestParam(defaultValue = "5") Integer size,
                                          @RequestParam(defaultValue = "0") Integer page) throws UserExepion {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());
        //        return ResponseEntity.ok(couponService.getCoupons());
        Page<Coupon> coupons = couponService.getCoupons(pageable);
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/deals")
    public ResponseEntity<?> getAllDeal(@AuthenticationPrincipal Users user,
                                          @RequestParam(defaultValue = "5") Integer size,
                                          @RequestParam(defaultValue = "0") Integer page) throws UserExepion {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());
        //        return ResponseEntity.ok(couponService.getCoupons());
        Page<Deal> coupons = dealService.findAll(pageable);
        return ResponseEntity.ok(coupons);
    }

    @PostMapping( value = "/create-collection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCollection (@ModelAttribute CollectionDTO collectionDTO) throws IOException {
        collectionService.save(collectionDTO);
        return ResponseEntity.ok("oke nha");
    }

    @GetMapping( value = "/get-all-collection")
    public ResponseEntity<?> getAllCollection () throws IOException {
        return ResponseEntity.ok(collectionService.findAll());
    }

    @GetMapping("/collection/first")
    public ResponseEntity<?> getFistCollection() {
        return ResponseEntity.ok(collectionService.findLastCollection());
    }

    @GetMapping("/collection/second")
    public ResponseEntity<?> getSecondCollection() {
        return ResponseEntity.ok(collectionService.findByLast());
    }

}
