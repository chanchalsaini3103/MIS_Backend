package com.example.internshipproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internshipproject.model.Brand;
import com.example.internshipproject.model.Chain;
import com.example.internshipproject.repo.BrandRepository;
import com.example.internshipproject.repo.ChainRepository;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private ChainRepository chainRepo;

    public List<Brand> getAllActiveBrands() {
        return brandRepo.findByIsActiveTrue();
    }

    public Brand addBrand(String brandName, Long chainId) {
        Chain chain = chainRepo.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Chain not found"));
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        brand.setChain(chain);
        return brandRepo.save(brand);
    }

    public Brand updateBrand(Long id, String brandName, Long chainId) {
        Brand brand = brandRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Chain chain = chainRepo.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Chain not found"));
        brand.setBrandName(brandName);
        brand.setChain(chain);
        return brandRepo.save(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        brand.setActive(false);
        brandRepo.save(brand);
    }

	public List<Brand> getBrandsByGroup(Long groupId) {
    return brandRepo.findByChain_Group_GroupIdAndIsActiveTrue(groupId);
}
}
