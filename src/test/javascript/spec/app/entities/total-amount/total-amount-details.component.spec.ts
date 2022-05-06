/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TotalAmountDetailComponent from '@/entities/total-amount/total-amount-details.vue';
import TotalAmountClass from '@/entities/total-amount/total-amount-details.component';
import TotalAmountService from '@/entities/total-amount/total-amount.service';
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
  describe('TotalAmount Management Detail Component', () => {
    let wrapper: Wrapper<TotalAmountClass>;
    let comp: TotalAmountClass;
    let totalAmountServiceStub: SinonStubbedInstance<TotalAmountService>;

    beforeEach(() => {
      totalAmountServiceStub = sinon.createStubInstance<TotalAmountService>(TotalAmountService);

      wrapper = shallowMount<TotalAmountClass>(TotalAmountDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { totalAmountService: () => totalAmountServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTotalAmount = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        totalAmountServiceStub.find.resolves(foundTotalAmount);

        // WHEN
        comp.retrieveTotalAmount('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.totalAmount).toBe(foundTotalAmount);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTotalAmount = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        totalAmountServiceStub.find.resolves(foundTotalAmount);

        // WHEN
        comp.beforeRouteEnter({ params: { totalAmountId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.totalAmount).toBe(foundTotalAmount);
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
