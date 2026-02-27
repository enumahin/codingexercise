package com.example.codingexercise.service;

import com.example.codingexercise.dto.PackageDto;
import java.util.List;

public interface PackageService {

    PackageDto getPackage(String packageId);

    List<PackageDto> getPackages();

    PackageDto createPackage(PackageDto packageDto);
}
