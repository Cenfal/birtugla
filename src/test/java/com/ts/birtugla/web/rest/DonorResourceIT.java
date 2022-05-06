package com.ts.birtugla.web.rest;

import static com.ts.birtugla.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ts.birtugla.IntegrationTest;
import com.ts.birtugla.domain.Donor;
import com.ts.birtugla.repository.DonorRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DonorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonorResourceIT {

    private static final Integer DEFAULT_INT_ID = 1;
    private static final Integer UPDATED_INT_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_BY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_BY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_NAME_VISIBLE = false;
    private static final Boolean UPDATED_NAME_VISIBLE = true;

    private static final Boolean DEFAULT_AMOUNT_VISIBLE = false;
    private static final Boolean UPDATED_AMOUNT_VISIBLE = true;

    private static final String ENTITY_API_URL = "/api/donors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonorMockMvc;

    private Donor donor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donor createEntity(EntityManager em) {
        Donor donor = new Donor()
            .intId(DEFAULT_INT_ID)
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createDate(DEFAULT_CREATE_DATE)
            .isDeleted(DEFAULT_IS_DELETED)
            .nameVisible(DEFAULT_NAME_VISIBLE)
            .amountVisible(DEFAULT_AMOUNT_VISIBLE);
        return donor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donor createUpdatedEntity(EntityManager em) {
        Donor donor = new Donor()
            .intId(UPDATED_INT_ID)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createDate(UPDATED_CREATE_DATE)
            .isDeleted(UPDATED_IS_DELETED)
            .nameVisible(UPDATED_NAME_VISIBLE)
            .amountVisible(UPDATED_AMOUNT_VISIBLE);
        return donor;
    }

    @BeforeEach
    public void initTest() {
        donor = createEntity(em);
    }

    @Test
    @Transactional
    void createDonor() throws Exception {
        int databaseSizeBeforeCreate = donorRepository.findAll().size();
        // Create the Donor
        restDonorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donor)))
            .andExpect(status().isCreated());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeCreate + 1);
        Donor testDonor = donorList.get(donorList.size() - 1);
        assertThat(testDonor.getIntId()).isEqualTo(DEFAULT_INT_ID);
        assertThat(testDonor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDonor.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testDonor.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testDonor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDonor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDonor.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDonor.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDonor.getNameVisible()).isEqualTo(DEFAULT_NAME_VISIBLE);
        assertThat(testDonor.getAmountVisible()).isEqualTo(DEFAULT_AMOUNT_VISIBLE);
    }

    @Test
    @Transactional
    void createDonorWithExistingId() throws Exception {
        // Create the Donor with an existing ID
        donorRepository.saveAndFlush(donor);

        int databaseSizeBeforeCreate = donorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donor)))
            .andExpect(status().isBadRequest());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDonors() throws Exception {
        // Initialize the database
        donorRepository.saveAndFlush(donor);

        // Get all the donorList
        restDonorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donor.getId().toString())))
            .andExpect(jsonPath("$.[*].intId").value(hasItem(DEFAULT_INT_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].nameVisible").value(hasItem(DEFAULT_NAME_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].amountVisible").value(hasItem(DEFAULT_AMOUNT_VISIBLE.booleanValue())));
    }

    @Test
    @Transactional
    void getDonor() throws Exception {
        // Initialize the database
        donorRepository.saveAndFlush(donor);

        // Get the donor
        restDonorMockMvc
            .perform(get(ENTITY_API_URL_ID, donor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donor.getId().toString()))
            .andExpect(jsonPath("$.intId").value(DEFAULT_INT_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.nameVisible").value(DEFAULT_NAME_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.amountVisible").value(DEFAULT_AMOUNT_VISIBLE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonor() throws Exception {
        // Get the donor
        restDonorMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonor() throws Exception {
        // Initialize the database
        donorRepository.saveAndFlush(donor);

        int databaseSizeBeforeUpdate = donorRepository.findAll().size();

        // Update the donor
        Donor updatedDonor = donorRepository.findById(donor.getId()).get();
        // Disconnect from session so that the updates on updatedDonor are not directly saved in db
        em.detach(updatedDonor);
        updatedDonor
            .intId(UPDATED_INT_ID)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createDate(UPDATED_CREATE_DATE)
            .isDeleted(UPDATED_IS_DELETED)
            .nameVisible(UPDATED_NAME_VISIBLE)
            .amountVisible(UPDATED_AMOUNT_VISIBLE);

        restDonorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDonor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDonor))
            )
            .andExpect(status().isOk());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
        Donor testDonor = donorList.get(donorList.size() - 1);
        assertThat(testDonor.getIntId()).isEqualTo(UPDATED_INT_ID);
        assertThat(testDonor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDonor.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDonor.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDonor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDonor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDonor.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDonor.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDonor.getNameVisible()).isEqualTo(UPDATED_NAME_VISIBLE);
        assertThat(testDonor.getAmountVisible()).isEqualTo(UPDATED_AMOUNT_VISIBLE);
    }

    @Test
    @Transactional
    void putNonExistingDonor() throws Exception {
        int databaseSizeBeforeUpdate = donorRepository.findAll().size();
        donor.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonor() throws Exception {
        int databaseSizeBeforeUpdate = donorRepository.findAll().size();
        donor.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonor() throws Exception {
        int databaseSizeBeforeUpdate = donorRepository.findAll().size();
        donor.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonorWithPatch() throws Exception {
        // Initialize the database
        donorRepository.saveAndFlush(donor);

        int databaseSizeBeforeUpdate = donorRepository.findAll().size();

        // Update the donor using partial update
        Donor partialUpdatedDonor = new Donor();
        partialUpdatedDonor.setId(donor.getId());

        partialUpdatedDonor
            .intId(UPDATED_INT_ID)
            .surname(UPDATED_SURNAME)
            .amount(UPDATED_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createDate(UPDATED_CREATE_DATE)
            .isDeleted(UPDATED_IS_DELETED);

        restDonorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonor))
            )
            .andExpect(status().isOk());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
        Donor testDonor = donorList.get(donorList.size() - 1);
        assertThat(testDonor.getIntId()).isEqualTo(UPDATED_INT_ID);
        assertThat(testDonor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDonor.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDonor.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testDonor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDonor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDonor.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDonor.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDonor.getNameVisible()).isEqualTo(DEFAULT_NAME_VISIBLE);
        assertThat(testDonor.getAmountVisible()).isEqualTo(DEFAULT_AMOUNT_VISIBLE);
    }

    @Test
    @Transactional
    void fullUpdateDonorWithPatch() throws Exception {
        // Initialize the database
        donorRepository.saveAndFlush(donor);

        int databaseSizeBeforeUpdate = donorRepository.findAll().size();

        // Update the donor using partial update
        Donor partialUpdatedDonor = new Donor();
        partialUpdatedDonor.setId(donor.getId());

        partialUpdatedDonor
            .intId(UPDATED_INT_ID)
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createDate(UPDATED_CREATE_DATE)
            .isDeleted(UPDATED_IS_DELETED)
            .nameVisible(UPDATED_NAME_VISIBLE)
            .amountVisible(UPDATED_AMOUNT_VISIBLE);

        restDonorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonor))
            )
            .andExpect(status().isOk());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
        Donor testDonor = donorList.get(donorList.size() - 1);
        assertThat(testDonor.getIntId()).isEqualTo(UPDATED_INT_ID);
        assertThat(testDonor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDonor.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testDonor.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testDonor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDonor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDonor.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDonor.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDonor.getNameVisible()).isEqualTo(UPDATED_NAME_VISIBLE);
        assertThat(testDonor.getAmountVisible()).isEqualTo(UPDATED_AMOUNT_VISIBLE);
    }

    @Test
    @Transactional
    void patchNonExistingDonor() throws Exception {
        int databaseSizeBeforeUpdate = donorRepository.findAll().size();
        donor.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonor() throws Exception {
        int databaseSizeBeforeUpdate = donorRepository.findAll().size();
        donor.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonor() throws Exception {
        int databaseSizeBeforeUpdate = donorRepository.findAll().size();
        donor.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(donor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Donor in the database
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonor() throws Exception {
        // Initialize the database
        donorRepository.saveAndFlush(donor);

        int databaseSizeBeforeDelete = donorRepository.findAll().size();

        // Delete the donor
        restDonorMockMvc
            .perform(delete(ENTITY_API_URL_ID, donor.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Donor> donorList = donorRepository.findAll();
        assertThat(donorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
