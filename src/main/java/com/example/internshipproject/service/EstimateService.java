package com.example.internshipproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internshipproject.model.Chain;
import com.example.internshipproject.model.Estimate;
import com.example.internshipproject.model.Zone;
import com.example.internshipproject.repo.ChainRepository;
import com.example.internshipproject.repo.EstimateRepository;
import com.example.internshipproject.repo.ZoneRepository;

@Service
public class EstimateService {

    @Autowired private EstimateRepository estimateRepository;
    @Autowired private ChainRepository chainRepository;
    @Autowired private ZoneRepository zoneRepository;

    public List<Estimate> getAllEstimates() {
        return estimateRepository.findAll();
    }

    public Optional<Estimate> getEstimate(Long id) {
        return estimateRepository.findById(id);
    }

    public Estimate createEstimate(Long chainId, Long zoneId, String service,
                                   int qty, float costPerUnit, float totalCost,
                                   LocalDate deliveryDate, String deliveryDetails) {
        Chain chain = chainRepository.findById(chainId).orElseThrow();
        Zone zone = zoneRepository.findById(zoneId).orElseThrow();

        Estimate estimate = new Estimate();
        estimate.setChain(chain);
        estimate.setGroupName(chain.getGroup().getGroupName());
        estimate.setBrandName(zone.getBrand().getBrandName());
        estimate.setZoneName(zone.getZoneName());
        estimate.setService(service);
        estimate.setQty(qty);
        estimate.setCostPerUnit(costPerUnit);
        estimate.setTotalCost(totalCost);
        estimate.setDeliveryDate(deliveryDate);
        estimate.setDeliveryDetails(deliveryDetails);

        return estimateRepository.save(estimate);
    }

    public Estimate updateEstimate(Long id, Estimate updatedEstimate) {
        Estimate estimate = estimateRepository.findById(id).orElseThrow();

        estimate.setService(updatedEstimate.getService());
        estimate.setQty(updatedEstimate.getQty());
        estimate.setCostPerUnit(updatedEstimate.getCostPerUnit());
        estimate.setTotalCost(updatedEstimate.getTotalCost());
        estimate.setDeliveryDate(updatedEstimate.getDeliveryDate());
        estimate.setDeliveryDetails(updatedEstimate.getDeliveryDetails());

        return estimateRepository.save(estimate);
    }

    public void deleteEstimate(Long id) {
        estimateRepository.deleteById(id);
    }
}
