<template>
  <div>
    <h2 id="page-heading" data-cy="TotalAmountHeading">
      <span v-text="$t('birtuglaApp.totalAmount.home.title')" id="total-amount-heading">Total Amounts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('birtuglaApp.totalAmount.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TotalAmountCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-total-amount"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('birtuglaApp.totalAmount.home.createLabel')"> Create a new Total Amount </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && totalAmounts && totalAmounts.length === 0">
      <span v-text="$t('birtuglaApp.totalAmount.home.notFound')">No totalAmounts found</span>
    </div>
    <div class="table-responsive" v-if="totalAmounts && totalAmounts.length > 0">
      <table class="table table-striped" aria-describedby="totalAmounts">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.totalAmount.amount')">Amount</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.totalAmount.version')">Version</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="totalAmount in totalAmounts" :key="totalAmount.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TotalAmountView', params: { totalAmountId: totalAmount.id } }">{{ totalAmount.id }}</router-link>
            </td>
            <td>{{ totalAmount.amount }}</td>
            <td>{{ totalAmount.version }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TotalAmountView', params: { totalAmountId: totalAmount.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TotalAmountEdit', params: { totalAmountId: totalAmount.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(totalAmount)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="birtuglaApp.totalAmount.delete.question" data-cy="totalAmountDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-totalAmount-heading" v-text="$t('birtuglaApp.totalAmount.delete.question', { id: removeId })">
          Are you sure you want to delete this Total Amount?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-totalAmount"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTotalAmount()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./total-amount.component.ts"></script>
