package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.PackageDto;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.PackageService;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
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
    public PackageDto getPackage(String packageId, boolean includeVoided) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PackageDto> getPackages(boolean includeVoided) {
        return packageRepository.findAll(
            includeVoided ? null : Specification.where(VoidedSpecification.notVoided()))
                .stream()
                .map(PackageDto::from)
                .collect(Collectors.toList());
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
        Package package = packageRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException(packageId));
        package.setVoided(voided);
        packageRepository.save(package);
    }
}
