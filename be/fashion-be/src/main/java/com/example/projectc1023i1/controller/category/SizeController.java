package com.example.projectc1023i1.controller.category;

import com.example.projectc1023i1.model.Size;
import com.example.projectc1023i1.model.Users;
import com.example.projectc1023i1.service.impl.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private ISizeService sizeService;
    @GetMapping("")
    public ResponseEntity<?> getSize(){
        return ResponseEntity.ok(sizeService.getallSize());
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getAllSizeOfOneProduct(@AuthenticationPrincipal Users users,
                                                    @RequestParam Integer productId){
        return ResponseEntity.ok(sizeService.getAllSizeOfOneProduct(productId));
    }
}
