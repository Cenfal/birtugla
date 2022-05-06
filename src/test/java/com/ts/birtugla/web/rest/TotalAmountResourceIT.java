package com.ts.birtugla.web.rest;

import static com.ts.birtugla.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ts.birtugla.IntegrationTest;
import com.ts.birtugla.domain.TotalAmount;
import com.ts.birtugla.repository.TotalAmountRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link TotalAmountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TotalAmountResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String ENTITY_API_URL = "/api/total-amounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TotalAmountRepository totalAmountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTotalAmountMockMvc;

    private TotalAmount totalAmount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalAmount createEntity(EntityManager em) {
        TotalAmount totalAmount = new TotalAmount().amount(DEFAULT_AMOUNT).version(DEFAULT_VERSION);
        return totalAmount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TotalAmount createUpdatedEntity(EntityManager em) {
        TotalAmount totalAmount = new TotalAmount().amount(UPDATED_AMOUNT).version(UPDATED_VERSION);
        return totalAmount;
    }

    @BeforeEach
    public void initTest() {
        totalAmount = createEntity(em);
    }

    @Test
    @Transactional
    void createTotalAmount() throws Exception {
        int databaseSizeBeforeCreate = totalAmountRepository.findAll().size();
        // Create the TotalAmount
        restTotalAmountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalAmount)))
            .andExpect(status().isCreated());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeCreate + 1);
        TotalAmount testTotalAmount = totalAmountList.get(totalAmountList.size() - 1);
        assertThat(testTotalAmount.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testTotalAmount.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void createTotalAmountWithExistingId() throws Exception {
        // Create the TotalAmount with an existing ID
        totalAmountRepository.saveAndFlush(totalAmount);

        int databaseSizeBeforeCreate = totalAmountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTotalAmountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalAmount)))
            .andExpect(status().isBadRequest());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTotalAmounts() throws Exception {
        // Initialize the database
        totalAmountRepository.saveAndFlush(totalAmount);

        // Get all the totalAmountList
        restTotalAmountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(totalAmount.getId().toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    void getTotalAmount() throws Exception {
        // Initialize the database
        totalAmountRepository.saveAndFlush(totalAmount);

        // Get the totalAmount
        restTotalAmountMockMvc
            .perform(get(ENTITY_API_URL_ID, totalAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(totalAmount.getId().toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    void getNonExistingTotalAmount() throws Exception {
        // Get the totalAmount
        restTotalAmountMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTotalAmount() throws Exception {
        // Initialize the database
        totalAmountRepository.saveAndFlush(totalAmount);

        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();

        // Update the totalAmount
        TotalAmount updatedTotalAmount = totalAmountRepository.findById(totalAmount.getId()).get();
        // Disconnect from session so that the updates on updatedTotalAmount are not directly saved in db
        em.detach(updatedTotalAmount);
        updatedTotalAmount.amount(UPDATED_AMOUNT).version(UPDATED_VERSION);

        restTotalAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTotalAmount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTotalAmount))
            )
            .andExpect(status().isOk());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
        TotalAmount testTotalAmount = totalAmountList.get(totalAmountList.size() - 1);
        assertThat(testTotalAmount.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTotalAmount.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void putNonExistingTotalAmount() throws Exception {
        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();
        totalAmount.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, totalAmount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTotalAmount() throws Exception {
        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();
        totalAmount.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalAmountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(totalAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTotalAmount() throws Exception {
        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();
        totalAmount.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalAmountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(totalAmount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTotalAmountWithPatch() throws Exception {
        // Initialize the database
        totalAmountRepository.saveAndFlush(totalAmount);

        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();

        // Update the totalAmount using partial update
        TotalAmount partialUpdatedTotalAmount = new TotalAmount();
        partialUpdatedTotalAmount.setId(totalAmount.getId());

        partialUpdatedTotalAmount.amount(UPDATED_AMOUNT).version(UPDATED_VERSION);

        restTotalAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalAmount))
            )
            .andExpect(status().isOk());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
        TotalAmount testTotalAmount = totalAmountList.get(totalAmountList.size() - 1);
        assertThat(testTotalAmount.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testTotalAmount.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void fullUpdateTotalAmountWithPatch() throws Exception {
        // Initialize the database
        totalAmountRepository.saveAndFlush(totalAmount);

        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();

        // Update the totalAmount using partial update
        TotalAmount partialUpdatedTotalAmount = new TotalAmount();
        partialUpdatedTotalAmount.setId(totalAmount.getId());

        partialUpdatedTotalAmount.amount(UPDATED_AMOUNT).version(UPDATED_VERSION);

        restTotalAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotalAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotalAmount))
            )
            .andExpect(status().isOk());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
        TotalAmount testTotalAmount = totalAmountList.get(totalAmountList.size() - 1);
        assertThat(testTotalAmount.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testTotalAmount.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    void patchNonExistingTotalAmount() throws Exception {
        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();
        totalAmount.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, totalAmount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTotalAmount() throws Exception {
        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();
        totalAmount.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalAmountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(totalAmount))
            )
            .andExpect(status().isBadRequest());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTotalAmount() throws Exception {
        int databaseSizeBeforeUpdate = totalAmountRepository.findAll().size();
        totalAmount.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalAmountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(totalAmount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TotalAmount in the database
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTotalAmount() throws Exception {
        // Initialize the database
        totalAmountRepository.saveAndFlush(totalAmount);

        int databaseSizeBeforeDelete = totalAmountRepository.findAll().size();

        // Delete the totalAmount
        restTotalAmountMockMvc
            .perform(delete(ENTITY_API_URL_ID, totalAmount.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TotalAmount> totalAmountList = totalAmountRepository.findAll();
        assertThat(totalAmountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
