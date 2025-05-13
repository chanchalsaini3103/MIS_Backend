package com.example.internshipproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internshipproject.model.Group;
import com.example.internshipproject.repo.GroupRepository;
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repo;

    @Override
    public Group addGroup(String name) {
        if (repo.existsByGroupName(name)) {
            throw new RuntimeException("Group Already Exists");
        }
        Group g = new Group();
        g.setGroupName(name);
        return repo.save(g);
    }

    @Override
    public Group updateGroup(Long id, String newName) {
        Group g = repo.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
        g.setGroupName(newName);
       
        return repo.save(g);
    }

    @Override
    public void deleteGroup(Long id) {
        Group g = repo.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
        g.setActive(false);
        repo.save(g);
    }

    @Override
    public List<Group> getAll() {
        return repo.findAll().stream()
                .filter(Group::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public Group getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
    }
}
