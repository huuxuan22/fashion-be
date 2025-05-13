package com.example.projectc1023i1.controller.admin;

import com.example.projectc1023i1.Dto.CouponDTO;
import com.example.projectc1023i1.Dto.DealDTO;
import com.example.projectc1023i1.Dto.EmployeeDTO;
import com.example.projectc1023i1.Exception.DataNotFoundException;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.model.*;
import com.example.projectc1023i1.request.GetInforEmployeeUpdate;
import com.example.projectc1023i1.request.UploadImageEmployee;
import com.example.projectc1023i1.respone.UserRespone;
import com.example.projectc1023i1.respone.errorsValidate.EmployeeErrorsRespone;
import com.example.projectc1023i1.service.impl.*;
import jakarta.validation.Valid;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryEmployeeService categoryEmployeeService;

    @Autowired
    private ICEmployeeDetailService detailService;

    @Autowired
    private IAddressUserService addressUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IDealService dealService;

    @Autowired
    private ICouponService couponService;

    /**
     *
     * @param size so luong nguoi dung
     * @param page so trang
     * @return tra ve danh sach nguoi dung va ma 200
     */
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser(@RequestParam("size") int size
                                        , @RequestParam("page") int page) {
        if (size <= 0 || page <= 0) {
            size = 10;
            page = 0;
        }
        Pageable pageable =  PageRequest.of(page,size);
        Page<Users> page1 = userService.getAllUser( pageable);
        return ResponseEntity.ok(page1);
    }




    @GetMapping("/get-all-category-employee")
    public ResponseEntity<?> getAllEmployee() {
        return ResponseEntity.ok(categoryEmployeeService.getAllCategoriesEmployee());
    }

//    @PostMapping("/add-new-employee")
//    public ResponseEntity<?> addNewEmployee(
//            @Valid @RequestBody EmployeeDTO employee,
//            BindingResult bindingResult,
//            HttpRequest request)
//            throws IOException, UserExepion {
//        EmployeeErrorsRespone combinedErrors = new EmployeeErrorsRespone();
//        if (bindingResult.hasErrors()) {
//            bindingResult.getFieldErrors().forEach(fieldError -> {
//                String errorMessage = fieldError.getDefaultMessage();
//                switch (fieldError.getField()) {
//                    case "fullName":
//                        combinedErrors.setFullName((combinedErrors.getFullName() != null ? combinedErrors.getFullName() + "," : "") + errorMessage);
//                        break;
//                    case "imgUrl":
//                        combinedErrors.setImgUrl((combinedErrors.getImgUrl() != null ? combinedErrors.getImgUrl() + "," : "") + errorMessage);
//                        break;
//                    case "age":
//                        combinedErrors.setAge((combinedErrors.getAge() != null ? combinedErrors.getAge() + "," : "") + errorMessage);
//                        break;
//                    case "gender":
//                        combinedErrors.setGender((combinedErrors.getGender() != null ? combinedErrors.getGender() + "," : "") + errorMessage);
//                        break;
//                    case "numberphone":
//                        combinedErrors.setNumberphone((combinedErrors.getNumberphone() != null ? combinedErrors.getNumberphone() + "," : "") + errorMessage);
//                        break;
//                    case "birthday":
//                        combinedErrors.setBirthday((combinedErrors.getBirthday() != null ? combinedErrors.getBirthday() + "," : "") + errorMessage);
//                        break;
//                    case "email":
//                        combinedErrors.setEmail((combinedErrors.getEmail() != null ? combinedErrors.getEmail() + "," : "") + errorMessage);
//                        break;
//
//                    default:
//                        break;
//                }
//            });
//            return  ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(combinedErrors);
//        }
//
//        AddressUser addressUser = new AddressUser();
//        Users users = userService.ConvertEmployeeDtoToUser(employee);
//        users.setRole(Roles.builder().roleId(1).roleName("EMPLOYEE").build());
//        users.setUsername(employee.getNumberphone());
//        users.setPassword(passwordEncoder.encode("nhanvien123"));
//        users.setIsActive(true);
////        if (employee.get)
//        userService.saveUser(users);
////        int id  = userService.f
//        Optional<Users> user = Optional.ofNullable(userService.findByNumerphone(employee.
//                getNumberphone()).orElseThrow(() -> new UserExepion("Người dùng này không tồn tại")));
//        addressUserService.addAddressUser(AddressUser.builder()
//                .user(user.get())
//                .province(employee.getProvince())
//                .district(employee.getDistrict())
//                .commune(employee.getCommune())
//                .homeAddress(employee.getNotes())
//                .build());
//        detailService.saveCED(CategoryEmployeeeDetail.builder()
//                .employeeCode(categoryEmployeeService.getCategoryEmployeeById(employee.getCategoryEmployee())
//                                    .categoryName+detailService.getIdMax())
//                        .salary(checkSalary(employee.getCategoryEmployee()))
//                        .user(user.get())
//                            .categoryEmployee(categoryEmployeeService.getCategoryEmployeeById(employee.getCategoryEmployee()))
//                .build());
//        return ResponseEntity.ok("Add New Employee succesfully");
//    }


//    @PutMapping("/update-employee")
//    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,BindingResult bindingResult) throws UserExepion {
//        EmployeeErrorsRespone combinedErrors = new EmployeeErrorsRespone();
//        if (bindingResult.hasErrors()) {
//            bindingResult.getFieldErrors().forEach(fieldError -> {
//                String errorMessage = fieldError.getDefaultMessage();
//                switch (fieldError.getField()) {
//                    case "fullName":
//                        combinedErrors.setFullName((combinedErrors.getFullName() != null ? combinedErrors.getFullName() + "," : "") + errorMessage);
//                        break;
//                    case "imgUrl":
//                        combinedErrors.setImgUrl((combinedErrors.getImgUrl() != null ? combinedErrors.getImgUrl() + "," : "") + errorMessage);
//                        break;
//                    case "age":
//                        combinedErrors.setAge((combinedErrors.getAge() != null ? combinedErrors.getAge() + "," : "") + errorMessage);
//                        break;
//                    case "gender":
//                        combinedErrors.setGender((combinedErrors.getGender() != null ? combinedErrors.getGender() + "," : "") + errorMessage);
//                        break;
//                    case "numberphone":
//                        combinedErrors.setNumberphone((combinedErrors.getNumberphone() != null ? combinedErrors.getNumberphone() + "," : "") + errorMessage);
//                        break;
//                    case "birthday":
//                        combinedErrors.setBirthday((combinedErrors.getBirthday() != null ? combinedErrors.getBirthday() + "," : "") + errorMessage);
//                        break;
//                    case "email":
//                        combinedErrors.setEmail((combinedErrors.getEmail() != null ? combinedErrors.getEmail() + "," : "") + errorMessage);
//                        break;
//
//                    default:
//                        break;
//                }
//            });
//            return  ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(combinedErrors);
//        }
//        Users user = userService.findByNumerphone(employeeDTO.getNumberphone()).get();
//        AddressUser addressUser = addressUserService.getAddressUser(user.getUserId());
//        addressUser.setProvince(employeeDTO.getProvince());
//        addressUser.setDistrict(employeeDTO.getDistrict());
//        addressUser.setCommune(employeeDTO.getCommune());
//        addressUser.setHomeAddress(employeeDTO.getNotes());
//        addressUserService.saveAddressUser(addressUser);
//        userService.saveUser(user);
//        return null;
//    }

    @PostMapping("/upload-image-employee")
    public ResponseEntity<?> uploadImageEmployee(
            @RequestBody UploadImageEmployee uploadImageEmployee
            ) {
        userService.findByNumerphone(uploadImageEmployee.getNumberphone())
                .orElseThrow(() -> new DataNotFoundException("Quá trình upload ảnh đang có vấn đề, bạn nên thực hiện lại thao tác"));

        if (uploadImageEmployee.getUrl() != null) {
            userService.uploadImgEmployee(uploadImageEmployee.getUrl(),uploadImageEmployee.getNumberphone());
        }
        return ResponseEntity.ok("OKE");
    }


    public double checkSalary (int value) {
        switch (value) {
            case 1:
                return 4000000;
            case 2:
                return 8000000;
            case 3:
                return 10000000;
            case 4:
                return 6000000;
        }
        return 0;
    }


    @PostMapping( value = "/create-deal",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDeal (@AuthenticationPrincipal Users users,
                                         @ModelAttribute DealDTO formData
                                         ) throws IOException {
        dealService.save(formData);
        logger.info("Sending private message to " + formData.toString());
        return ResponseEntity.ok("");
    }
    private static final Logger logger = LoggerFactory.getLogger(PrivateChat.class);

    @GetMapping( "/deal/get-all")
    public ResponseEntity<?> getAllDeal (@AuthenticationPrincipal Users users,
                                         @RequestParam("size") Integer size,
                                         @RequestParam("page") Integer page
    ) throws IOException {
        Pageable pageable =  PageRequest.of(page,size, Sort.by("startTime").descending());
        return ResponseEntity.ok(dealService.findAll(pageable));
    }
    @GetMapping("/deal/page")
    public ResponseEntity<?> getAllPageOfDeal() {
        return ResponseEntity.ok(dealService.getAllPageOfDeals());
    }


    @PutMapping("/deal/update")
    public ResponseEntity<?> updateDeal (@AuthenticationPrincipal Users users,
                                         @RequestBody Deal deal) {
        dealService.updateStatus(deal);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/deal")
    public ResponseEntity<?> deleteDeal (@AuthenticationPrincipal Users users,
                                         @RequestParam("dealId") Integer dealId) {
        dealService.delete(dealId);
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/coupon/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCoupon (@ModelAttribute CouponDTO formData) throws IOException {
        couponService.createCoupon(formData);
        logger.info("Sending private message to " + formData.toString());
        return ResponseEntity.ok("");
    }

    @GetMapping( "/coupon/get-all")
    public ResponseEntity<?> getAllCoupon (@AuthenticationPrincipal Users users,
                                         @RequestParam("size") Integer size,
                                         @RequestParam("page") Integer page
    ) throws IOException {
        Pageable pageable =  PageRequest.of(page,size, Sort.by("startTime").descending());
        return ResponseEntity.ok(couponService.getCoupons(pageable));
    }
    @GetMapping("/coupon/page")
    public ResponseEntity<?> getAllPageOfCoupon() {
        return ResponseEntity.ok(couponService.countTotalCoupons());
    }



}
