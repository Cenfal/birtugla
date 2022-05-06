/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import DonorService from '@/entities/donor/donor.service';
import { Donor } from '@/shared/model/donor.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Donor Service', () => {
    let service: DonorService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new DonorService();
      currentDate = new Date();
      elemDefault = new Donor(
        '9fec3727-3421-4967-b213-ba36557ca194',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        false,
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: dayjs(currentDate).format(DATE_FORMAT),
            createDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('9fec3727-3421-4967-b213-ba36557ca194').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('9fec3727-3421-4967-b213-ba36557ca194')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Donor', async () => {
        const returnedFromService = Object.assign(
          {
            id: '9fec3727-3421-4967-b213-ba36557ca194',
            createdBy: dayjs(currentDate).format(DATE_FORMAT),
            createDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdBy: currentDate,
            createDate: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Donor', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Donor', async () => {
        const returnedFromService = Object.assign(
          {
            intId: 1,
            name: 'BBBBBB',
            surname: 'BBBBBB',
            amount: 1,
            description: 'BBBBBB',
            createdBy: dayjs(currentDate).format(DATE_FORMAT),
            createDate: dayjs(currentDate).format(DATE_FORMAT),
            isDeleted: true,
            nameVisible: true,
            amountVisible: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdBy: currentDate,
            createDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Donor', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Donor', async () => {
        const patchObject = Object.assign(
          {
            intId: 1,
            amount: 1,
            description: 'BBBBBB',
            createdBy: dayjs(currentDate).format(DATE_FORMAT),
            amountVisible: true,
          },
          new Donor()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdBy: currentDate,
            createDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Donor', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Donor', async () => {
        const returnedFromService = Object.assign(
          {
            intId: 1,
            name: 'BBBBBB',
            surname: 'BBBBBB',
            amount: 1,
            description: 'BBBBBB',
            createdBy: dayjs(currentDate).format(DATE_FORMAT),
            createDate: dayjs(currentDate).format(DATE_FORMAT),
            isDeleted: true,
            nameVisible: true,
            amountVisible: true,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdBy: currentDate,
            createDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Donor', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Donor', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('9fec3727-3421-4967-b213-ba36557ca194').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Donor', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('9fec3727-3421-4967-b213-ba36557ca194')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
