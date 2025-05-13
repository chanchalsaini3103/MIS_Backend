
package com.example.internshipproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internshipproject.model.Brand;
import com.example.internshipproject.model.Zone;
import com.example.internshipproject.repo.BrandRepository;
import com.example.internshipproject.repo.ZoneRepository;

@Service
public class ZoneService {
    @Autowired private ZoneRepository zoneRepo;
    @Autowired private BrandRepository brandRepo;

    public List<Zone> getAllZones() {
        return zoneRepo.findByIsActiveTrue();
    }

    public Zone addZone(String name, Long brandId) {
        Brand brand = brandRepo.findById(brandId).orElseThrow();
        Zone z = new Zone();
        z.setZoneName(name);
        z.setBrand(brand);
        return zoneRepo.save(z);
    }

    public Zone updateZone(Long id, String name, Long brandId) {
        Zone zone = zoneRepo.findById(id).orElseThrow();
        Brand brand = brandRepo.findById(brandId).orElseThrow();
        zone.setZoneName(name);
        zone.setBrand(brand);
        return zoneRepo.save(zone);
    }

    public void deleteZone(Long id) {
        Zone z = zoneRepo.findById(id).orElseThrow();
        z.setActive(false);
        zoneRepo.save(z);
    }
}
