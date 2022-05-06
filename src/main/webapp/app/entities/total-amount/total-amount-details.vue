<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="totalAmount">
        <h2 class="jh-entity-heading" data-cy="totalAmountDetailsHeading">
          <span v-text="$t('birtuglaApp.totalAmount.detail.title')">TotalAmount</span> {{ totalAmount.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('birtuglaApp.totalAmount.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ totalAmount.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('birtuglaApp.totalAmount.version')">Version</span>
          </dt>
          <dd>
            <span>{{ totalAmount.version }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="totalAmount.id"
          :to="{ name: 'TotalAmountEdit', params: { totalAmountId: totalAmount.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./total-amount-details.component.ts"></script>
