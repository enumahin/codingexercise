package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.PackageDto;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.PackageService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Default {@link PackageService} implementation that persists packages via {@link PackageRepository}.
 */
@AllArgsConstructor
@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PackageDto getPackage(String packageId) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PackageDto> getPackages() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PackageDto createPackage(PackageDto packageDto) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PackageDto updatePackage(String packageId, PackageDto packageDto) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePackage(String packageId) {
    }
}
