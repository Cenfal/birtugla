import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITotalAmount } from '@/shared/model/total-amount.model';

import TotalAmountService from '@/entities/total-amount/total-amount.service';
import AlertService from '@/shared/alert/alert.service';
import {IDonor} from "@/shared/model/donor.model";
import DonorService from "@/entities/donor/donor.service";
import {BProgress, BTable} from 'bootstrap-vue'
import {IDonorsForTable} from "@/shared/model/donor-for-table.model";
Vue.component('b-table', BTable)
@Component({
  mixins: [Vue2Filters.mixin]
})
export default class MainPage extends Vue {
  @Inject('totalAmountService') private totalAmountService: () => TotalAmountService;
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('donorService') private donorService: () => DonorService;
  private removeId: string = null;
  public donors: IDonor[] = [];
  public donorsForTable: IDonorsForTable[] = [];
  public totalAmounts: ITotalAmount[] = [];
  public isFetching = false;
  public value =44
  public max =100

  public mounted(): void {
    this.retrieveLastTotalAmount();
    this.retrieveDonorsForTable();
    this.retrieveDonors()
  }

  public retrieveLastTotalAmount(): void {
    this.isFetching = true;
    this.totalAmountService()
      .retrieveLastTotalAmount()
      .then(
        res => {
          this.totalAmounts = res.data;
          this.isFetching = false;
          this.value=res.data;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public retrieveDonorsForTable(): void {
    this.isFetching = true;
    this.donorService()
      .retrieveDonorsForTable()
      .then(
        res => {
          this.donorsForTable = res.data.content;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public retrieveDonors(): void {
    this.isFetching = true;
    this.donorService()
      .retrieve()
      .then(
        res => {
          this.donors = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }
}
