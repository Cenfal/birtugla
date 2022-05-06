<template>
  <div>
    etst
    <h2 id="page-heading" data-cy="DonorHeading">
      <span v-text="$t('birtuglaApp.donor.home.title')" id="donor-heading">Donors</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('birtuglaApp.donor.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DonorCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-donor"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('birtuglaApp.donor.home.createLabel')"> Create a new Donor </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && donors && donors.length === 0">
      <span v-text="$t('birtuglaApp.donor.home.notFound')">No donors found</span>
    </div>
    <div class="table-responsive" v-if="donors && donors.length > 0">
      <table class="table table-striped" aria-describedby="donors">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.intId')">Int Id</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.name')">Name</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.surname')">Surname</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.amount')">Amount</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.description')">Description</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.createdBy')">Created By</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.createDate')">Create Date</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.isDeleted')">Is Deleted</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.nameVisible')">Name Visible</span></th>
            <th scope="row"><span v-text="$t('birtuglaApp.donor.amountVisible')">Amount Visible</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="donor in donors" :key="donor.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DonorView', params: { donorId: donor.id } }">{{ donor.id }}</router-link>
            </td>
            <td>{{ donor.intId }}</td>
            <td>{{ donor.name }}</td>
            <td>{{ donor.surname }}</td>
            <td>{{ donor.amount }}</td>
            <td>{{ donor.description }}</td>
            <td>{{ donor.createdBy }}</td>
            <td>{{ donor.createDate }}</td>
            <td>{{ donor.isDeleted }}</td>
            <td>{{ donor.nameVisible }}</td>
            <td>{{ donor.amountVisible }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DonorView', params: { donorId: donor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DonorEdit', params: { donorId: donor.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(donor)"
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
        ><span id="birtuglaApp.donor.delete.question" data-cy="donorDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-donor-heading" v-text="$t('birtuglaApp.donor.delete.question', { id: removeId })">
          Are you sure you want to delete this Donor?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-donor"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDonor()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./donor.component.ts"></script>
