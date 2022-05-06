import { Component, Provide, Vue } from 'vue-property-decorator';

import DonorService from './donor/donor.service';
import TotalAmountService from './total-amount/total-amount.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('donorService') private donorService = () => new DonorService();
  @Provide('totalAmountService') private totalAmountService = () => new TotalAmountService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
