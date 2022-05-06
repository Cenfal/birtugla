package com.ts.birtugla.repository;

import com.ts.birtugla.domain.TotalAmount;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TotalAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TotalAmountRepository extends JpaRepository<TotalAmount, UUID> {
    @Query(value = "select * from total_amount order by version desc ", nativeQuery = true)
    List<TotalAmount> getCurrentTotalAMount();
}
