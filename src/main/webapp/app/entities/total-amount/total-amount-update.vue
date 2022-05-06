<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="birtuglaApp.totalAmount.home.createOrEditLabel"
          data-cy="TotalAmountCreateUpdateHeading"
          v-text="$t('birtuglaApp.totalAmount.home.createOrEditLabel')"
        >
          Create or edit a TotalAmount
        </h2>
        <div>
          <div class="form-group" v-if="totalAmount.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="totalAmount.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.totalAmount.amount')" for="total-amount-amount">Amount</label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="total-amount-amount"
              data-cy="amount"
              :class="{ valid: !$v.totalAmount.amount.$invalid, invalid: $v.totalAmount.amount.$invalid }"
              v-model.number="$v.totalAmount.amount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('birtuglaApp.totalAmount.version')" for="total-amount-version">Version</label>
            <input
              type="number"
              class="form-control"
              name="version"
              id="total-amount-version"
              data-cy="version"
              :class="{ valid: !$v.totalAmount.version.$invalid, invalid: $v.totalAmount.version.$invalid }"
              v-model.number="$v.totalAmount.version.$model"
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
            :disabled="$v.totalAmount.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./total-amount-update.component.ts"></script>
