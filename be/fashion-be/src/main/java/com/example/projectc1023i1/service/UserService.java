package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.EmployeeDTO;
import com.example.projectc1023i1.Dto.UserDTO;
import com.example.projectc1023i1.Dto.UserUpdateDTO;
import com.example.projectc1023i1.Exception.PayloadTooLargeException;
import com.example.projectc1023i1.Exception.UnsuportedMediaTypeException;
import com.example.projectc1023i1.Exception.UserExepion;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.repository.impl.IUserRepository;
import com.example.projectc1023i1.request.UpdateUserRequest;
import com.example.projectc1023i1.respone.UserRespone;
import com.example.projectc1023i1.service.impl.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<Users> getAllUser(org.springframework.data.domain.Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Users updateUser(Users users, UserUpdateDTO userDTO) throws UserExepion, IOException {
        String filename = "";
        if (userDTO.getImage() != null) {
            filename = storeFile(userDTO.getImage());
            MultipartFile file = userDTO.getImage();
            if (file.getSize() > 1024 * 1024) {
                throw  new PayloadTooLargeException("anh qua lon, lon hon 10Byte");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.contains("image/")) {
                throw  new UnsuportedMediaTypeException("Không hỗ trợ loại ảnh này ");
            }
        }
        users.setEmail(userDTO.getEmail());
        users.setImgUrl(filename);
        users.setGender(userDTO.getGender());
        users.setFullName(userDTO.getFullName());
        users.setBirthday(Timestamp.valueOf(userDTO.getBirthday()));
        users.setNumberphone(userDTO.getNumberphone());
        return userRepository.save(users);
    }

    public String storeFile(MultipartFile file) throws com.example.projectc1023i1.Exception.IOException, java.io.IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new com.example.projectc1023i1.Exception.IOException("Ảnh bạn chọn không phù hợp ");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
        Path uploadDir = Paths.get("uploads/user");
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    public static boolean isImageFile(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return false;
        }
        String contentType = file.getContentType().toLowerCase();
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/jpg");
    }


    @Override
    public List<Users> searchUsers(String value) {
        return List.of();
    }

    @Override
    public Users findUserById(Integer userId) throws UserExepion {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } throw new UserExepion("User not found by id: "+ userId);
    }

    @Override
    public UserRespone convertUserToUserRespone(Users users) {
        return modelMapper.map(users, UserRespone.class);
    }

    @Override
    public Users ConvertEmployeeDtoToUser(EmployeeDTO employeeDto) {
        return modelMapper.map(employeeDto, Users.class);
    }

    @Override
    public Users saveUser(Users users) throws UserExepion {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return userRepository.save(users);
    }

    @Override
    public Optional<Users> findByNumerphone(String phone) {
        return userRepository.findByNumberphone(phone);
    }

    @Override
    public void uploadImgEmployee(String url, String numberPhone) {
        userRepository.updateImage(url, numberPhone);
    }

    @Override
    public Users convertUserDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, Users.class);
    }

    @Override
    public Users findByUserName(String userName) {
        return userRepository.findByUsername(userName).orElse(null);
    }

    @Override
    public void changePassword(Users users) {
        userRepository.save(users);
    }


}
