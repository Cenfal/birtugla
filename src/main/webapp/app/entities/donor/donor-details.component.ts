import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDonor } from '@/shared/model/donor.model';
import DonorService from './donor.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DonorDetails extends Vue {
  @Inject('donorService') private donorService: () => DonorService;
  @Inject('alertService') private alertService: () => AlertService;

  public donor: IDonor = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.donorId) {
        vm.retrieveDonor(to.params.donorId);
      }
    });
  }

  public retrieveDonor(donorId) {
    this.donorService()
      .find(donorId)
      .then(res => {
        this.donor = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
