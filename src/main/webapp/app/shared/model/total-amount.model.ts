export interface ITotalAmount {
  id?: string;
  amount?: number | null;
  version?: number | null;
}

export class TotalAmount implements ITotalAmount {
  constructor(public id?: string, public amount?: number | null, public version?: number | null) {}
}
