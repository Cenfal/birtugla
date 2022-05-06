import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IDonor, Donor } from '@/shared/model/donor.model';
import DonorService from './donor.service';

const validations: any = {
  donor: {
    intId: {},
    name: {},
    surname: {},
    amount: {},
    description: {},
    createdBy: {},
    createDate: {},
    isDeleted: {},
    nameVisible: {},
    amountVisible: {},
  },
};

@Component({
  validations,
})
export default class DonorUpdate extends Vue {
  @Inject('donorService') private donorService: () => DonorService;
  @Inject('alertService') private alertService: () => AlertService;

  public donor: IDonor = new Donor();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.donorId) {
        vm.retrieveDonor(to.params.donorId);
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
    if (this.donor.id) {
      this.donorService()
        .update(this.donor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('birtuglaApp.donor.updated', { param: param.id });
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
      this.donorService()
        .create(this.donor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('birtuglaApp.donor.created', { param: param.id });
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

  public retrieveDonor(donorId): void {
    this.donorService()
      .find(donorId)
      .then(res => {
        this.donor = res;
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
