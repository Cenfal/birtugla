package com.ts.birtugla.service;

import com.ts.birtugla.domain.Donor;
import com.ts.birtugla.domain.DonorsForTable;
import com.ts.birtugla.repository.DonorRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DonorService {
    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Page<DonorsForTable> getDonorsForTable(Pageable pageable){
       return donorRepository.getAllDonorsForTable( pageable);
    }
}
