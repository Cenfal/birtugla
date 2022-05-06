import axios from 'axios';

import { ITotalAmount } from '@/shared/model/total-amount.model';

const baseApiUrl = 'api/total-amounts';
const lastTotalAmountUrl = 'api/current-total-amount';


export default class TotalAmountService {
  public find(id: string): Promise<ITotalAmount> {
    return new Promise<ITotalAmount>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
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

  public delete(id: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ITotalAmount): Promise<ITotalAmount> {
    return new Promise<ITotalAmount>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ITotalAmount): Promise<ITotalAmount> {
    return new Promise<ITotalAmount>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: ITotalAmount): Promise<ITotalAmount> {
    return new Promise<ITotalAmount>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
