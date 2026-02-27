package com.example.codingexercise.controller;

import com.example.codingexercise.dto.PackageDto;
import com.example.codingexercise.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping()
    public ResponseEntity<PackageDto> createPackage(@RequestBody PackageDto packageDto) {
        return ResponseEntity.ok(packageService.createPackage(packageDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageDto> getPackage(@PathVariable String id) {
        return ResponseEntity.ok(packageService.getPackage(id));
    }
}
