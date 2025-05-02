package com.example.internshipproject.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.internshipproject.model.Chain;
import com.example.internshipproject.model.Group;
import com.example.internshipproject.repo.ChainRepository;
import com.example.internshipproject.repo.GroupRepository;

@RestController
@RequestMapping("/api/chains")
@CrossOrigin(origins = {
		  "http://localhost:5173",
		  "https://groupmanagement-frontend.onrender.com"
		})
public class ChainController {

	@Autowired
	ChainRepository chainRepository;

	@Autowired
	GroupRepository groupRepository;

	@PostMapping
	public ResponseEntity<?> addChain(@RequestBody Chain chain)
	{
		if(chainRepository.existsByGstnNo(chain.getGstnNo())) {
			return ResponseEntity.badRequest().body("GSTN already exists.");	}
		chain.setCreatedAt(LocalDateTime.now());
		chain.setUpdatedAt(LocalDateTime.now());
		
		chainRepository.save(chain);
		return ResponseEntity.ok("Company added Successfully");

	
	}
	
	@GetMapping
	public List<Chain> getAllChains()
	{
		return chainRepository.findByIsActiveTrue();
	}
	
	@GetMapping("/filter")
	public List<Chain> getByGroup(@RequestParam String groupName)
	{
		return chainRepository.findByGroupGroupNameAndIsActiveTrue(groupName);
	}
	
	@PutMapping("/{id}")
	 public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Chain updated) {
        Optional<Chain> chainOpt = chainRepository.findById(id);
        if (chainOpt.isEmpty()) return ResponseEntity.notFound().build();
        Chain chain = chainOpt.get();
        chain.setCompanyName(updated.getCompanyName());
        chain.setGstnNo(updated.getGstnNo());
        chain.setGroup(updated.getGroup());
        chain.setUpdatedAt(LocalDateTime.now());
        chainRepository.save(chain);
        return ResponseEntity.ok("Updated");
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Chain> chainOpt = chainRepository.findById(id);
        if (chainOpt.isEmpty()) return ResponseEntity.notFound().build();
        Chain chain = chainOpt.get();

        

        chain.setIsActive(false); 
        chainRepository.save(chain);
        return ResponseEntity.ok("Deleted");
    }
	
	@GetMapping("/api/groups")
	public List<Group> getAllGroups() {
	    return groupRepository.findAll();  
	}


}
