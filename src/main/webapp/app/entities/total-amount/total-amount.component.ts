import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITotalAmount } from '@/shared/model/total-amount.model';

import TotalAmountService from './total-amount.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TotalAmount extends Vue {
  @Inject('totalAmountService') private totalAmountService: () => TotalAmountService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public totalAmounts: ITotalAmount[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTotalAmounts();
  }

  public clear(): void {
    this.retrieveAllTotalAmounts();
  }

  public retrieveAllTotalAmounts(): void {
    this.isFetching = true;
    this.totalAmountService()
      .retrieve()
      .then(
        res => {
          this.totalAmounts = res.data;
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

  public prepareRemove(instance: ITotalAmount): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTotalAmount(): void {
    this.totalAmountService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('birtuglaApp.totalAmount.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTotalAmounts();
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
