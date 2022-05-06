export interface IDonor {
  id?: string;
  intId?: number | null;
  name?: string | null;
  surname?: string | null;
  amount?: number | null;
  description?: string | null;
  createdBy?: Date | null;
  createDate?: Date | null;
  isDeleted?: boolean | null;
  nameVisible?: boolean | null;
  amountVisible?: boolean | null;
}

export class Donor implements IDonor {
  constructor(
    public id?: string,
    public intId?: number | null,
    public name?: string | null,
    public surname?: string | null,
    public amount?: number | null,
    public description?: string | null,
    public createdBy?: Date | null,
    public createDate?: Date | null,
    public isDeleted?: boolean | null,
    public nameVisible?: boolean | null,
    public amountVisible?: boolean | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.nameVisible = this.nameVisible ?? false;
    this.amountVisible = this.amountVisible ?? false;
  }
}
