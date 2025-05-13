
package com.example.internshipproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internshipproject.model.Brand;
import com.example.internshipproject.service.BrandService;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://mia-frontend-mu0v.onrender.com"
	}, allowCredentials = "true")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<Brand> getAll() {
        return brandService.getAllActiveBrands();
    }

    @PostMapping
    public ResponseEntity<Brand> add(@RequestBody Map<String, String> data) {
        Brand b = brandService.addBrand(data.get("brandName"), Long.parseLong(data.get("chainId")));
        return ResponseEntity.ok(b);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id, @RequestBody Map<String, String> data) {
        return ResponseEntity.ok(
            brandService.updateBrand(id, data.get("brandName"), Long.parseLong(data.get("chainId")))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/group/{groupId}")
    public List<Brand> getBrandsByGroup(@PathVariable Long groupId) {
        return brandService.getBrandsByGroup(groupId);
    }

}
