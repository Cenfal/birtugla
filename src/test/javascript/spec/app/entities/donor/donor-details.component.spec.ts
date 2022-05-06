/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DonorDetailComponent from '@/entities/donor/donor-details.vue';
import DonorClass from '@/entities/donor/donor-details.component';
import DonorService from '@/entities/donor/donor.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Donor Management Detail Component', () => {
    let wrapper: Wrapper<DonorClass>;
    let comp: DonorClass;
    let donorServiceStub: SinonStubbedInstance<DonorService>;

    beforeEach(() => {
      donorServiceStub = sinon.createStubInstance<DonorService>(DonorService);

      wrapper = shallowMount<DonorClass>(DonorDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { donorService: () => donorServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDonor = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        donorServiceStub.find.resolves(foundDonor);

        // WHEN
        comp.retrieveDonor('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.donor).toBe(foundDonor);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDonor = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        donorServiceStub.find.resolves(foundDonor);

        // WHEN
        comp.beforeRouteEnter({ params: { donorId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.donor).toBe(foundDonor);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
