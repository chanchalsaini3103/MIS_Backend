package com.example.internshipproject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internshipproject.model.Chain;
import com.example.internshipproject.model.Group;
import com.example.internshipproject.repo.ChainRepository;
import com.example.internshipproject.repo.GroupRepository;

@Service
public class ChainService {

    @Autowired
    private ChainRepository chainRepo;

    @Autowired
    private GroupRepository groupRepo;

    public List<Chain> getAllChains() {
        return chainRepo.findByIsActiveTrue();
    }

    public List<Chain> getChainsByGroup(Long groupId) {
        return chainRepo.findByGroup_GroupIdAndIsActiveTrue(groupId);
    }

    public Chain addChain(String name, String gstn, Long groupId) {
        if (chainRepo.findByGstnNo(gstn).isPresent()) {
            throw new RuntimeException("GSTN already exists.");
        }

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Chain chain = new Chain();
        chain.setCompanyName(name);
        chain.setGstnNo(gstn);
        chain.setGroup(group);
        chain.setCreatedAt(LocalDateTime.now());
        chain.setUpdatedAt(LocalDateTime.now());
        return chainRepo.save(chain);
    }

    public Chain updateChain(Long id, String name, String gstn, Long groupId) {
        Chain chain = chainRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        chain.setCompanyName(name);
        chain.setGstnNo(gstn);
        chain.setGroup(group);
        chain.setUpdatedAt(LocalDateTime.now());
        return chainRepo.save(chain);
    }

    public void softDeleteChain(Long id) {
        Chain chain = chainRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        // TODO: Add check for brand relation before allowing delete (in future)
        chain.setActive(false);
        chain.setUpdatedAt(LocalDateTime.now());
        chainRepo.save(chain);
    }

    public Chain getChainById(Long id) {
        return chainRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Chain not found"));
    }

}
