/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DonorComponent from '@/entities/donor/donor.vue';
import DonorClass from '@/entities/donor/donor.component';
import DonorService from '@/entities/donor/donor.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Donor Management Component', () => {
    let wrapper: Wrapper<DonorClass>;
    let comp: DonorClass;
    let donorServiceStub: SinonStubbedInstance<DonorService>;

    beforeEach(() => {
      donorServiceStub = sinon.createStubInstance<DonorService>(DonorService);
      donorServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DonorClass>(DonorComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          donorService: () => donorServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      donorServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllDonors();
      await comp.$nextTick();

      // THEN
      expect(donorServiceStub.retrieve.called).toBeTruthy();
      expect(comp.donors[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      donorServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(donorServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDonor();
      await comp.$nextTick();

      // THEN
      expect(donorServiceStub.delete.called).toBeTruthy();
      expect(donorServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
