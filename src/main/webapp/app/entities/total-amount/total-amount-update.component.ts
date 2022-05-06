import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ITotalAmount, TotalAmount } from '@/shared/model/total-amount.model';
import TotalAmountService from './total-amount.service';

const validations: any = {
  totalAmount: {
    amount: {},
    version: {},
  },
};

@Component({
  validations,
})
export default class TotalAmountUpdate extends Vue {
  @Inject('totalAmountService') private totalAmountService: () => TotalAmountService;
  @Inject('alertService') private alertService: () => AlertService;

  public totalAmount: ITotalAmount = new TotalAmount();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.totalAmountId) {
        vm.retrieveTotalAmount(to.params.totalAmountId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.totalAmount.id) {
      this.totalAmountService()
        .update(this.totalAmount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('birtuglaApp.totalAmount.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.totalAmountService()
        .create(this.totalAmount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('birtuglaApp.totalAmount.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveTotalAmount(totalAmountId): void {
    this.totalAmountService()
      .find(totalAmountId)
      .then(res => {
        this.totalAmount = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
