import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITotalAmount } from '@/shared/model/total-amount.model';
import TotalAmountService from './total-amount.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TotalAmountDetails extends Vue {
  @Inject('totalAmountService') private totalAmountService: () => TotalAmountService;
  @Inject('alertService') private alertService: () => AlertService;

  public totalAmount: ITotalAmount = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.totalAmountId) {
        vm.retrieveTotalAmount(to.params.totalAmountId);
      }
    });
  }

  public retrieveTotalAmount(totalAmountId) {
    this.totalAmountService()
      .find(totalAmountId)
      .then(res => {
        this.totalAmount = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
