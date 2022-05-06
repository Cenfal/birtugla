/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TotalAmountUpdateComponent from '@/entities/total-amount/total-amount-update.vue';
import TotalAmountClass from '@/entities/total-amount/total-amount-update.component';
import TotalAmountService from '@/entities/total-amount/total-amount.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('TotalAmount Management Update Component', () => {
    let wrapper: Wrapper<TotalAmountClass>;
    let comp: TotalAmountClass;
    let totalAmountServiceStub: SinonStubbedInstance<TotalAmountService>;

    beforeEach(() => {
      totalAmountServiceStub = sinon.createStubInstance<TotalAmountService>(TotalAmountService);

      wrapper = shallowMount<TotalAmountClass>(TotalAmountUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          totalAmountService: () => totalAmountServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.totalAmount = entity;
        totalAmountServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(totalAmountServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.totalAmount = entity;
        totalAmountServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(totalAmountServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTotalAmount = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        totalAmountServiceStub.find.resolves(foundTotalAmount);
        totalAmountServiceStub.retrieve.resolves([foundTotalAmount]);

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
