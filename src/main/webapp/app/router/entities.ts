import { Authority } from '@/shared/security/authority';
import MainPage from "@/entities/main-page/main-page.component";
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Donor = () => import('@/entities/donor/donor.vue');
// prettier-ignore
const DonorUpdate = () => import('@/entities/donor/donor-update.vue');
// prettier-ignore
const DonorDetails = () => import('@/entities/donor/donor-details.vue');
// prettier-ignore
const TotalAmount = () => import('@/entities/total-amount/total-amount.vue');
// prettier-ignore
const TotalAmountUpdate = () => import('@/entities/total-amount/total-amount-update.vue');
// prettier-ignore
const TotalAmountDetails = () => import('@/entities/total-amount/total-amount-details.vue');
// prettier-ignore
const MainPage = () => import('@/entities/main-page/main-page.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'donor',
      name: 'Donor',
      component: Donor,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'donor/new',
      name: 'DonorCreate',
      component: DonorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'donor/:donorId/edit',
      name: 'DonorEdit',
      component: DonorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'donor/:donorId/view',
      name: 'DonorView',
      component: DonorDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'total-amount',
      name: 'TotalAmount',
      component: TotalAmount,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'total-amount/new',
      name: 'TotalAmountCreate',
      component: TotalAmountUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'total-amount/:totalAmountId/edit',
      name: 'TotalAmountEdit',
      component: TotalAmountUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'total-amount/:totalAmountId/view',
      name: 'TotalAmountView',
      component: TotalAmountDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
    {
      path: 'main-page',
      name: 'mainpage',
      component: MainPage,
      meta: { authorities: [Authority.USER] },
    }
  ],
};
