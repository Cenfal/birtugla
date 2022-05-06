package com.ts.birtugla.web.rest;

import com.ts.birtugla.domain.TotalAmount;
import com.ts.birtugla.repository.TotalAmountRepository;
import com.ts.birtugla.web.rest.errors.BadRequestAlertException;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ts.birtugla.domain.TotalAmount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TotalAmountResource {

    private final Logger log = LoggerFactory.getLogger(TotalAmountResource.class);

    private static final String ENTITY_NAME = "totalAmount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TotalAmountRepository totalAmountRepository;

    public TotalAmountResource(TotalAmountRepository totalAmountRepository) {
        this.totalAmountRepository = totalAmountRepository;
    }

    /**
     * {@code POST  /total-amounts} : Create a new totalAmount.
     *
     * @param totalAmount the totalAmount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new totalAmount, or with status {@code 400 (Bad Request)} if the totalAmount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/total-amounts")
    public ResponseEntity<TotalAmount> createTotalAmount(@RequestBody TotalAmount totalAmount) throws URISyntaxException {
        log.debug("REST request to save TotalAmount : {}", totalAmount);
        if (totalAmount.getId() != null) {
            throw new BadRequestAlertException("A new totalAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TotalAmount result = totalAmountRepository.save(totalAmount);
        return ResponseEntity
            .created(new URI("/api/total-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/current-total-amount")
    public ResponseEntity<BigDecimal> currentTotalAmount(){
        List<TotalAmount> all=totalAmountRepository.findAll();
        List<TotalAmount> totalAmounts=totalAmountRepository.getCurrentTotalAMount();
        BigDecimal currentTotalAmount= totalAmounts.get(0).getAmount();
        return ResponseEntity.ok(currentTotalAmount);
    }


    /**
     * {@code PUT  /total-amounts/:id} : Updates an existing totalAmount.
     *
     * @param id the id of the totalAmount to save.
     * @param totalAmount the totalAmount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated totalAmount,
     * or with status {@code 400 (Bad Request)} if the totalAmount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the totalAmount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PutMapping("/total-amounts/{id}")
    public ResponseEntity<TotalAmount> updateTotalAmount(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody TotalAmount totalAmount
    ) throws URISyntaxException {
        log.debug("REST request to update TotalAmount : {}, {}", id, totalAmount);
        if (totalAmount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, totalAmount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalAmountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TotalAmount result = totalAmountRepository.save(totalAmount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, totalAmount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /total-amounts/:id} : Partial updates given fields of an existing totalAmount, field will ignore if it is null
     *
     * @param id the id of the totalAmount to save.
     * @param totalAmount the totalAmount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated totalAmount,
     * or with status {@code 400 (Bad Request)} if the totalAmount is not valid,
     * or with status {@code 404 (Not Found)} if the totalAmount is not found,
     * or with status {@code 500 (Internal Server Error)} if the totalAmount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/total-amounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TotalAmount> partialUpdateTotalAmount(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody TotalAmount totalAmount
    ) throws URISyntaxException {
        log.debug("REST request to partial update TotalAmount partially : {}, {}", id, totalAmount);
        if (totalAmount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, totalAmount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalAmountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TotalAmount> result = totalAmountRepository
            .findById(totalAmount.getId())
            .map(existingTotalAmount -> {
                if (totalAmount.getAmount() != null) {
                    existingTotalAmount.setAmount(totalAmount.getAmount());
                }
                if (totalAmount.getVersion() != null) {
                    existingTotalAmount.setVersion(totalAmount.getVersion());
                }

                return existingTotalAmount;
            })
            .map(totalAmountRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, totalAmount.getId().toString())
        );
    }

    /**
     * {@code GET  /total-amounts} : get all the totalAmounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of totalAmounts in body.
     */
    @GetMapping("/total-amounts")
    public List<TotalAmount> getAllTotalAmounts() {
        log.debug("REST request to get all TotalAmounts");
        return totalAmountRepository.findAll();
    }

    /**
     * {@code GET  /total-amounts/:id} : get the "id" totalAmount.
     *
     * @param id the id of the totalAmount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the totalAmount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/total-amounts/{id}")
    public ResponseEntity<TotalAmount> getTotalAmount(@PathVariable UUID id) {
        log.debug("REST request to get TotalAmount : {}", id);
        Optional<TotalAmount> totalAmount = totalAmountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(totalAmount);
    }

    /**
     * {@code DELETE  /total-amounts/:id} : delete the "id" totalAmount.
     *
     * @param id the id of the totalAmount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/total-amounts/{id}")
    public ResponseEntity<Void> deleteTotalAmount(@PathVariable UUID id) {
        log.debug("REST request to delete TotalAmount : {}", id);
        totalAmountRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
