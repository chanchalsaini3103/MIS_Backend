package com.example.internshipproject.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.internshipproject.model.Group;
import com.example.internshipproject.service.GroupService;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://mia-frontend-mu0v.onrender.com"
    },
    allowCredentials = "true"
)
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService service;

    @GetMapping
    public ResponseEntity<List<Group>> allGroups() {
        logger.info("Fetching all groups");
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> req) {
        String name = req.get("groupName");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Group name is required");
        }
        try {
            logger.info("Adding new group: {}", name);
            return ResponseEntity.ok(service.addGroup(name));
        } catch (RuntimeException e) {
            logger.error("Error while adding group: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, String> req) {
        String name = req.get("groupName");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Group name is required");
        }
        try {
            logger.info("Updating group with ID {}: new name = {}", id, name);
            return ResponseEntity.ok(service.updateGroup(id, name));
        } catch (RuntimeException e) {
            logger.error("Error while updating group: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found or update failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            logger.info("Deleting group with ID {}", id);
            service.deleteGroup(id);
            return ResponseEntity.ok("Group deleted");
        } catch (RuntimeException e) {
            logger.error("Error while deleting group: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found or delete failed");
        }
    }
}
