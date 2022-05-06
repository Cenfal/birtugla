import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDonor } from '@/shared/model/donor.model';

import DonorService from './donor.service';
import AlertService from '@/shared/alert/alert.service';
import {IDonorsForTable} from "@/shared/model/donor-for-table.model";

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Donor extends Vue {
  @Inject('donorService') private donorService: () => DonorService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public donors: IDonor[] = [];
  public donorsFortable: IDonorsForTable[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDonors();
  }

  public clear(): void {
    this.retrieveAllDonors();
  }

  public retrieveAllDonors(): void {
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

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IDonor): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDonor(): void {
    this.donorService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('birtuglaApp.donor.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDonors();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
