package com.example.projectc1023i1.controller.category;

import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.service.ColorService;
import com.example.projectc1023i1.service.impl.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {
    @Autowired
    private IColorService colorService;
    @GetMapping("")
    public ResponseEntity<?> getAllColor() {
        List<Color> colorList = colorService.getColors();
        return ResponseEntity.ok(colorList);
    }
}
