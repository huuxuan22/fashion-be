package com.example.projectc1023i1.controller.users;


import com.example.projectc1023i1.Dto.AddressUserDTO;
import com.example.projectc1023i1.Dto.CollectionDTO;
import com.example.projectc1023i1.Dto.UserDTO;
import com.example.projectc1023i1.Dto.UserUpdateDTO;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.component.JwtTokenUtils;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.request.UpdateUserRequest;
import com.example.projectc1023i1.respone.ApiRespone;
import com.example.projectc1023i1.service.DealService;
import com.example.projectc1023i1.service.ProductService;
import com.example.projectc1023i1.service.UserService;
import com.example.projectc1023i1.service.VerificationService;
import com.example.projectc1023i1.service.impl.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IDealService dealService;
    @Autowired
    private ICollectionService collectionService;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils tokenUtils;

    @GetMapping("/profile")
    public ResponseEntity<Users> getUserProfileHandler (@AuthenticationPrincipal Users user) throws UserExepion {
        return new ResponseEntity<Users>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{value}")
    public ResponseEntity<List<Users>> getUserHandler (@PathVariable String value) throws UserExepion {
        List<Users> users = userService.searchUsers(value);
        return  new ResponseEntity<List<Users>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiRespone> updateProfile (@ModelAttribute UserUpdateDTO userDTO,
                                                     @AuthenticationPrincipal Users users) throws UserExepion, IOException {

        Users userUpdate = userService.updateUser(users,userDTO);
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

    @GetMapping("/address")
    public ResponseEntity<?> getAllArress(@AuthenticationPrincipal Users users) {
        List<AddressUser> addressUsers = addressUserService.getAddressUser(users.getUserId());
        return ResponseEntity.ok(addressUsers);
    }

    @PostMapping("/add-new-address")
    public ResponseEntity<?> addnewAddress (@AuthenticationPrincipal Users users,
                                            @RequestBody AddressUserDTO addressUserDTO) throws UserExepion {
        addressUserService.addNewAddress(addressUserDTO,users);
        return ResponseEntity.ok("oke nha");
    }

    @PostMapping("/update-address")
    public ResponseEntity<?> updateAddress (@AuthenticationPrincipal Users users,
                                            @RequestBody AddressUser addressUser) throws UserExepion {
        addressUserService.updateAddress(addressUser,users);
        return ResponseEntity.ok("oke nha");
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAddress (@AuthenticationPrincipal Users users,
                                            @RequestParam("id") Integer id) throws UserExepion {
        addressUserService.deleteAddressUser(id);
        return ResponseEntity.ok("oke nha");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal Users users,
                                            @RequestParam("oldPassword") String oldPassword) {

        boolean check = passwordEncoder.matches(oldPassword,users.getPassword());
        if (check) {
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword (@AuthenticationPrincipal Users users,
                                             @RequestParam("newPassword") String newPassword,
                                             @RequestParam("code") String code) {
        boolean flag = verificationService.verifyCode(users.getEmail(), code);
        if (!flag) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mã xác thực không đúng");
        }
        users.setPassword(passwordEncoder.encode(newPassword));
        userService.changePassword(users);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(),newPassword)
        );
        Users register = (Users) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(register);
        return ResponseEntity.ok(jwt);
    }

    /**
     * lay ma giam gia
     */
    @GetMapping("/get-coupon")
    public ResponseEntity<?> getCoupon(@AuthenticationPrincipal Users users,
                                       @RequestParam("code") String code) {
        Coupon coupon = couponService.findByCouponCode(code);
        Map<String, Object> response = new HashMap<>();
        if (coupon == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã không tồn tại");
        }else if (coupon.getUsageLimit() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("số lượng đã hết");
        } else  {
            return ResponseEntity.ok(coupon);
        }
    }

    @GetMapping("/get-deal")
    public ResponseEntity<?> getDeal(@AuthenticationPrincipal Users users,
                                       @RequestParam("productId") Integer productId) {
//        Deal;
        List<Deal> dealList = dealService.findByProduct(productId);
        return  ResponseEntity.ok(dealList);
    }

    @PostMapping("/decrease-coupon")
    public ResponseEntity<?> decreaseCoupon(@AuthenticationPrincipal Users users,
                                            @RequestBody Coupon coupon) {
//        Deal;
        couponService.decreaseOneCouponQuality(coupon);
        return  ResponseEntity.ok("oke nha");
    }
}
