/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TotalAmountComponent from '@/entities/total-amount/total-amount.vue';
import TotalAmountClass from '@/entities/total-amount/total-amount.component';
import TotalAmountService from '@/entities/total-amount/total-amount.service';
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
  describe('TotalAmount Management Component', () => {
    let wrapper: Wrapper<TotalAmountClass>;
    let comp: TotalAmountClass;
    let totalAmountServiceStub: SinonStubbedInstance<TotalAmountService>;

    beforeEach(() => {
      totalAmountServiceStub = sinon.createStubInstance<TotalAmountService>(TotalAmountService);
      totalAmountServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TotalAmountClass>(TotalAmountComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          totalAmountService: () => totalAmountServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      totalAmountServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllTotalAmounts();
      await comp.$nextTick();

      // THEN
      expect(totalAmountServiceStub.retrieve.called).toBeTruthy();
      expect(comp.totalAmounts[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      totalAmountServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(totalAmountServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTotalAmount();
      await comp.$nextTick();

      // THEN
      expect(totalAmountServiceStub.delete.called).toBeTruthy();
      expect(totalAmountServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
