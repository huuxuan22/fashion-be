package com.example.projectc1023i1.controller.login_register;

import com.example.projectc1023i1.Dto.UserDTO;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.component.JwtTokenUtils;
import com.example.projectc1023i1.model.Roles;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.request.LoginRequest;
import com.example.projectc1023i1.respone.ErrorRespones;
import com.example.projectc1023i1.respone.errorsValidate.LoginErrors;
import com.example.projectc1023i1.respone.errorsValidate.RegisterErrors;
import com.example.projectc1023i1.service.RedisService;
import com.example.projectc1023i1.service.UserService;
import com.example.projectc1023i1.service.VerificationService;
import com.example.projectc1023i1.service.impl.IUserService;
import com.example.projectc1023i1.utils.GetTokenFromRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private JwtTokenUtils tokenUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private VerificationService verificationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult
    ) {
        LoginErrors loginErrorsDTO = new LoginErrors();
        Users user = userService.findByUserName(loginRequest.getUsername());
        boolean flag = false;
        if (user != null) {
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                loginErrorsDTO.setPassword("mật khẩu không chính xác");
                flag = true;
            }
        }
        if (bindingResult.hasErrors() || flag) {
            bindingResult.getFieldErrors().stream()
                    .forEach(fieldError -> {
                        String field = fieldError.getField();
                        String message = fieldError.getDefaultMessage();
                        switch (field) {
                            case "username":
                                loginErrorsDTO.setUsername(
                                        loginErrorsDTO.getUsername() == null ? message :
                                                loginErrorsDTO.getUsername() + "; " + message
                                );
                                break;
                            case "password":
                                loginErrorsDTO.setPassword(
                                        loginErrorsDTO.getPassword() == null ? message :
                                                loginErrorsDTO.getPassword() + "; " + message
                                );
                                break;
                        }
                    });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginErrorsDTO);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            // Lấy thông tin người dùng từ Authentication object
            Users userToken = (Users) authentication.getPrincipal();
            String jwt = jwtTokenUtils.generateToken(userToken);
            return ResponseEntity.ok(jwt);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorRespones("Tài khoản hoặc mật khẩu không đúng.", HttpStatus.UNAUTHORIZED.value()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra. Vui lòng thử lại sau.: "+ e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal Users user,
                                    HttpServletRequest request) {
        String token = GetTokenFromRequest.getTokenFromRequest(request);
        if (token != null) {
            String username = jwtTokenUtils.extractUserName(token);
            redisService.addTokenList(username,token);
            return ResponseEntity.ok("Đăng xuất thành công");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Không tìm thấy token hợp lệ");
        }
    }



    @PutMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO,
                                      BindingResult bindingResult) throws UserExepion {
        if (bindingResult.hasErrors()) {
            RegisterErrors registerErrors = new RegisterErrors();
            bindingResult.getFieldErrors().forEach(fieldError -> {
               String field = fieldError.getField();
               String message = fieldError.getDefaultMessage();
                switch (field) {
                    case "gender":
                        registerErrors.setGender(
                                registerErrors.getGender() == null ? message :
                                        registerErrors.getGender() + "; " + message
                        );
                        break;
                    case "numberphone":
                        registerErrors.setNumberphone(
                                registerErrors.getNumberphone() == null ? message :
                                        registerErrors.getNumberphone() + "; " + message
                        );
                        break;
                    case "fullName":
                        registerErrors.setFullName(
                                registerErrors.getFullName() == null ? message :
                                        registerErrors.getFullName() + "; " + message
                        );
                        break;
                    case "username":
                        registerErrors.setUsername(
                                registerErrors.getUsername() == null ? message :
                                        registerErrors.getUsername() + "; " + message
                        );
                        break;
                    case "password":
                        registerErrors.setPassword(
                                registerErrors.getPassword() == null ? message :
                                        registerErrors.getPassword() + "; " + message
                        );
                        break;
                    case "birthday":
                        registerErrors.setBirthday(
                                registerErrors.getBirthday() == null ? message :
                                        registerErrors.getBirthday() + "; " + message
                        );
                        break;
                    case "email":
                        registerErrors.setEmail(
                                registerErrors.getEmail() == null ? message :
                                        registerErrors.getEmail() + "; " + message
                        );
                        break;
                }
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerErrors);
        }
        verificationService.sendVerificationCode(userDTO.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/send-again")
    public ResponseEntity<?> sendCodeAgain(@RequestParam String email) {
        verificationService.sendVerificationCode(email);
        return ResponseEntity.ok(email);
    }

    @PostMapping("/save")
    public ResponseEntity<?> veryCodeForSave (
                                              @RequestParam String code,
                                              @RequestBody UserDTO userDTO) throws UserExepion {
        boolean flag = verificationService.verifyCode(userDTO.getEmail(), code);
        if (!flag) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mã xác thực không đúng");
        }
        Users user = userService.convertUserDTOToUser(userDTO);
        user.setRole(Roles.builder().roleId(1).roleName(Roles.USER).build());
        userService.saveUser(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword())
        );
        Users register = (Users) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(register);
        return ResponseEntity.ok(jwt);
    }
}
