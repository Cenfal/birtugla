package com.ts.birtugla.web.rest;

import com.ts.birtugla.domain.Donor;
import com.ts.birtugla.domain.DonorsForTable;
import com.ts.birtugla.repository.DonorRepository;
import com.ts.birtugla.service.DonorService;
import com.ts.birtugla.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ts.birtugla.domain.Donor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@RequiredArgsConstructor
public class DonorResource {

    private final Logger log = LoggerFactory.getLogger(DonorResource.class);

    private static final String ENTITY_NAME = "donor";

    private String applicationName;

    private final DonorRepository donorRepository;
    private final DonorService donorService;

    public DonorResource(@Value("${jhipster.clientApp.name}") String applicationName, DonorRepository donorRepository, DonorService donorService) {
        this.applicationName = applicationName;
        this.donorRepository = donorRepository;
        this.donorService = donorService;
    }


    /**
     * {@code POST  /donors} : Create a new donor.
     *
     * @param donor the donor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donor, or with status {@code 400 (Bad Request)} if the donor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donors")
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) throws URISyntaxException {
        log.debug("REST request to save Donor : {}", donor);
        if (donor.getId() != null) {
            throw new BadRequestAlertException("A new donor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Donor result = donorRepository.save(donor);
        return ResponseEntity
            .created(new URI("/api/donors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/donors-for-table")
    public Page<DonorsForTable> donorsForTable(Pageable pageable){
        return donorService.getDonorsForTable(pageable);
    }

    /**
     * {@code PUT  /donors/:id} : Updates an existing donor.
     *
     * @param id the id of the donor to save.
     * @param donor the donor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donor,
     * or with status {@code 400 (Bad Request)} if the donor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donors/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Donor donor)
        throws URISyntaxException {
        log.debug("REST request to update Donor : {}, {}", id, donor);
        if (donor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Donor result = donorRepository.save(donor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donors/:id} : Partial updates given fields of an existing donor, field will ignore if it is null
     *
     * @param id the id of the donor to save.
     * @param donor the donor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donor,
     * or with status {@code 400 (Bad Request)} if the donor is not valid,
     * or with status {@code 404 (Not Found)} if the donor is not found,
     * or with status {@code 500 (Internal Server Error)} if the donor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Donor> partialUpdateDonor(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Donor donor)
        throws URISyntaxException {
        log.debug("REST request to partial update Donor partially : {}, {}", id, donor);
        if (donor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Donor> result = donorRepository
            .findById(donor.getId())
            .map(existingDonor -> {
                if (donor.getIntId() != null) {
                    existingDonor.setIntId(donor.getIntId());
                }
                if (donor.getName() != null) {
                    existingDonor.setName(donor.getName());
                }
                if (donor.getSurname() != null) {
                    existingDonor.setSurname(donor.getSurname());
                }
                if (donor.getAmount() != null) {
                    existingDonor.setAmount(donor.getAmount());
                }
                if (donor.getDescription() != null) {
                    existingDonor.setDescription(donor.getDescription());
                }
                if (donor.getCreatedBy() != null) {
                    existingDonor.setCreatedBy(donor.getCreatedBy());
                }
                if (donor.getCreateDate() != null) {
                    existingDonor.setCreateDate(donor.getCreateDate());
                }
                if (donor.getIsDeleted() != null) {
                    existingDonor.setIsDeleted(donor.getIsDeleted());
                }
                if (donor.getNameVisible() != null) {
                    existingDonor.setNameVisible(donor.getNameVisible());
                }
                if (donor.getAmountVisible() != null) {
                    existingDonor.setAmountVisible(donor.getAmountVisible());
                }

                return existingDonor;
            })
            .map(donorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donor.getId().toString())
        );
    }

    /**
     * {@code GET  /donors} : get all the donors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donors in body.
     */
    @GetMapping("/donors")
    public List<Donor> getAllDonors() {
        log.debug("REST request to get all Donors");
        return donorRepository.findAll();
    }

    /**
     * {@code GET  /donors/:id} : get the "id" donor.
     *
     * @param id the id of the donor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donors/{id}")
    public ResponseEntity<Donor> getDonor(@PathVariable UUID id) {
        log.debug("REST request to get Donor : {}", id);
        Optional<Donor> donor = donorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(donor);
    }

    /**
     * {@code DELETE  /donors/:id} : delete the "id" donor.
     *
     * @param id the id of the donor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donors/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable UUID id) {
        log.debug("REST request to delete Donor : {}", id);
        donorRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
