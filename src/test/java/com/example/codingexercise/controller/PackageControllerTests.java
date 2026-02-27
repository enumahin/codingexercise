package com.example.codingexercise.controller;

import com.example.codingexercise.model.Package;
import com.example.codingexercise.repository.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackageControllerTests {

	private final TestRestTemplate restTemplate;
    private final PackageRepository packageRepository;

    @Autowired
    PackageControllerTests(TestRestTemplate restTemplate, PackageRepository packageRepository) {
		this.restTemplate = restTemplate;
        this.packageRepository = packageRepository;
    }

    @Test
    void createPackage() {
//		ResponseEntity<Package> created = restTemplate.postForEntity("/packages", new Package(null, "Test Name", "Test Desc", List.of("prod1")), Package.class);
//        assertEquals(HttpStatus.OK, created.getStatusCode(), "Unexpected status code");
//        Package createdBody = created.getBody();
//        assertNotNull(createdBody, "Unexpected body");
//        assertEquals("Test Name", createdBody.getName(), "Unexpected name");
//        assertEquals("Test Desc", createdBody.getDescription(), "Unexpected description");
//        assertEquals(List.of("prod1"), createdBody.getProductIds(), "Unexpected products");
//
//        Package aPackage = packageRepository.get(createdBody.getId());
//        assertNotNull(aPackage, "Unexpected package");
//        assertEquals(createdBody.getId(), aPackage.getId(), "Unexpected id");
//        assertEquals(createdBody.getName(), aPackage.getName(), "Unexpected name");
//        assertEquals(createdBody.getDescription(), aPackage.getDescription(), "Unexpected description");
//        assertEquals(createdBody.getProductIds(), aPackage.getProductIds(), "Unexpected products");
    }

    @Test
    void getPackage() {
//        Package aPackage = packageRepository.create("Test Name 2", "Test Desc 2", List.of("prod2"));
//        ResponseEntity<Package> fetched = restTemplate.getForEntity("/packages/{id}", Package.class, aPackage.getId());
//        assertEquals(HttpStatus.OK, fetched.getStatusCode(), "Unexpected status code");
//        Package fetchedBody = fetched.getBody();
//        assertNotNull(fetchedBody, "Unexpected body");
//        assertEquals(aPackage.getId(), fetchedBody.getId(), "Unexpected id");
//        assertEquals(aPackage.getName(), fetchedBody.getName(), "Unexpected name");
//        assertEquals(aPackage.getDescription(), fetchedBody.getDescription(), "Unexpected description");
//        assertEquals(aPackage.getProductIds(), fetchedBody.getProductIds(), "Unexpected products");
    }

    @Test
    void listPackages() {
//        Package package1 = packageRepository.create("Test Name 1", "Test Desc 1", List.of("prod1"));
//        Package package2 = packageRepository.create("Test Name 2", "Test Desc 2", List.of("prod2"));
//
//        ResponseEntity<Object> fetched = restTemplate.getForEntity("/packages", Object.class);
//        assertEquals(HttpStatus.OK, fetched.getStatusCode(), "Unexpected status code");
    }

}
