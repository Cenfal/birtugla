package com.ts.birtugla.repository;

import com.ts.birtugla.domain.Donor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.ts.birtugla.domain.DonorsForTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Donor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonorRepository extends JpaRepository<Donor, UUID> {
    @Query(value = "select new com.ts.birtugla.domain.DonorsForTable(d.name , d.surname, d.amount, d.description, d.createDate) from Donor d order by d.createDate")
    public Page<DonorsForTable> getAllDonorsForTable(Pageable pageable);
}
