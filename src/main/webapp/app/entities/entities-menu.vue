<template>
  <div>
    <b-dropdown-item to="/donor">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.donor')">Donor</span>
    </b-dropdown-item>
    <b-dropdown-item to="/total-amount">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.totalAmount')">Total Amount</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
