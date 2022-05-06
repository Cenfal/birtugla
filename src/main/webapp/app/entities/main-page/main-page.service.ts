import axios from 'axios';

import { ITotalAmount } from '@/shared/model/total-amount.model';
import {IDonor} from "@/shared/model/donor.model";

const totalAmountApiUrl = 'api/total-amounts';
const donorsApiUrl = 'api/donors';
const lastTotalAmountUrl = 'api/current-total-amount';

export default class MainPageService {
  public retrieveTotalAmount(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(totalAmountApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  public retrieveLastTotalAmount(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(lastTotalAmountUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findDonor(id: string): Promise<IDonor> {
    return new Promise<IDonor>((resolve, reject) => {
      axios
        .get(`${donorsApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveDonors(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(donorsApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
