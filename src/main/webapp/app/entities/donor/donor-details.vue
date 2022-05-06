<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="donor">
        <h2 class="jh-entity-heading" data-cy="donorDetailsHeading">
          <span v-text="$t('birtuglaApp.donor.detail.title')">Donor</span> {{ donor.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('birtuglaApp.donor.intId')">Int Id</span>
          </dt>
          <dd>
            <span>{{ donor.intId }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.name')">Name</span>
          </dt>
          <dd>
            <span>{{ donor.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.surname')">Surname</span>
          </dt>
          <dd>
            <span>{{ donor.surname }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ donor.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.description')">Description</span>
          </dt>
          <dd>
            <span>{{ donor.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ donor.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.createDate')">Create Date</span>
          </dt>
          <dd>
            <span>{{ donor.createDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.isDeleted')">Is Deleted</span>
          </dt>
          <dd>
            <span>{{ donor.isDeleted }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.nameVisible')">Name Visible</span>
          </dt>
          <dd>
            <span>{{ donor.nameVisible }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.donor.amountVisible')">Amount Visible</span>
          </dt>
          <dd>
            <span>{{ donor.amountVisible }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="donor.id" :to="{ name: 'DonorEdit', params: { donorId: donor.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./donor-details.component.ts"></script>
