package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.PackageDto;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.PackageService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    @Override
    public PackageDto getPackage(String packageId) {
        return null;
    }

    @Override
    public List<PackageDto> getPackages() {
        return List.of();
    }

    @Override
    public PackageDto createPackage(PackageDto packageDto) {
        return null;
    }

    @Override
    public Object updatePackage(String id, PackageDto packageDto) {
        return null;
    }

    @Override
    public void deletePackage(String id) {
        UUID packageUuid;
        try {
            packageUuid = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new EntityNotFoundException("Package not found for id: " + id);
        }

        if (!packageRepository.existsById(packageUuid)) {
            throw new EntityNotFoundException("Package not found for id: " + id);
        }

        packageRepository.deleteById(packageUuid);
    }
}
