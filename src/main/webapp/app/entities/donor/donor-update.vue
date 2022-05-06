<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="birtuglaApp.donor.home.createOrEditLabel"
          data-cy="DonorCreateUpdateHeading"
          v-text="$t('birtuglaApp.donor.home.createOrEditLabel')"
        >
          Create or edit a Donor
        </h2>
        <div>
          <div class="form-group" v-if="donor.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="donor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.intId')" for="donor-intId">Int Id</label>
            <input
              type="number"
              class="form-control"
              name="intId"
              id="donor-intId"
              data-cy="intId"
              :class="{ valid: !$v.donor.intId.$invalid, invalid: $v.donor.intId.$invalid }"
              v-model.number="$v.donor.intId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.name')" for="donor-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="donor-name"
              data-cy="name"
              :class="{ valid: !$v.donor.name.$invalid, invalid: $v.donor.name.$invalid }"
              v-model="$v.donor.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.surname')" for="donor-surname">Surname</label>
            <input
              type="text"
              class="form-control"
              name="surname"
              id="donor-surname"
              data-cy="surname"
              :class="{ valid: !$v.donor.surname.$invalid, invalid: $v.donor.surname.$invalid }"
              v-model="$v.donor.surname.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.amount')" for="donor-amount">Amount</label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="donor-amount"
              data-cy="amount"
              :class="{ valid: !$v.donor.amount.$invalid, invalid: $v.donor.amount.$invalid }"
              v-model.number="$v.donor.amount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.description')" for="donor-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="donor-description"
              data-cy="description"
              :class="{ valid: !$v.donor.description.$invalid, invalid: $v.donor.description.$invalid }"
              v-model="$v.donor.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.createdBy')" for="donor-createdBy">Created By</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="donor-createdBy"
                  v-model="$v.donor.createdBy.$model"
                  name="createdBy"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="donor-createdBy"
                data-cy="createdBy"
                type="text"
                class="form-control"
                name="createdBy"
                :class="{ valid: !$v.donor.createdBy.$invalid, invalid: $v.donor.createdBy.$invalid }"
                v-model="$v.donor.createdBy.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.createDate')" for="donor-createDate">Create Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="donor-createDate"
                  v-model="$v.donor.createDate.$model"
                  name="createDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="donor-createDate"
                data-cy="createDate"
                type="text"
                class="form-control"
                name="createDate"
                :class="{ valid: !$v.donor.createDate.$invalid, invalid: $v.donor.createDate.$invalid }"
                v-model="$v.donor.createDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.isDeleted')" for="donor-isDeleted">Is Deleted</label>
            <input
              type="checkbox"
              class="form-check"
              name="isDeleted"
              id="donor-isDeleted"
              data-cy="isDeleted"
              :class="{ valid: !$v.donor.isDeleted.$invalid, invalid: $v.donor.isDeleted.$invalid }"
              v-model="$v.donor.isDeleted.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.nameVisible')" for="donor-nameVisible">Name Visible</label>
            <input
              type="checkbox"
              class="form-check"
              name="nameVisible"
              id="donor-nameVisible"
              data-cy="nameVisible"
              :class="{ valid: !$v.donor.nameVisible.$invalid, invalid: $v.donor.nameVisible.$invalid }"
              v-model="$v.donor.nameVisible.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.donor.amountVisible')" for="donor-amountVisible"
              >Amount Visible</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="amountVisible"
              id="donor-amountVisible"
              data-cy="amountVisible"
              :class="{ valid: !$v.donor.amountVisible.$invalid, invalid: $v.donor.amountVisible.$invalid }"
              v-model="$v.donor.amountVisible.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.donor.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./donor-update.component.ts"></script>
