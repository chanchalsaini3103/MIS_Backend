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

import com.example.internshipproject.model.Zone;
import com.example.internshipproject.service.ZoneService;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = { "http://localhost:5173", "https://mis-and-invoicing-system-frontend.onrender.com" }, allowCredentials = "true")
public class ZoneController {

    @Autowired private ZoneService zoneService;

    @GetMapping
    public List<Zone> getAllZones() {
        return zoneService.getAllZones();
    }

    @PostMapping
    public ResponseEntity<Zone> addZone(@RequestBody Map<String, String> req) {
        return ResponseEntity.ok(zoneService.addZone(req.get("zoneName"), Long.parseLong(req.get("brandId"))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zone> updateZone(@PathVariable Long id, @RequestBody Map<String, String> req) {
        return ResponseEntity.ok(zoneService.updateZone(id, req.get("zoneName"), Long.parseLong(req.get("brandId"))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
