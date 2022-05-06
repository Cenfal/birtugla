export interface IDonorsForTable {
  name?: string | null;
  surname?: string | null;
  amount?: number | null;
  description?: string | null;
  createDate?: Date | null;
}

export class Donor implements IDonorsForTable {
  constructor(
    public name?: string | null,
    public surname?: string | null,
    public amount?: number | null,
    public description?: string | null,
    public createDate?: Date | null,

  ) {
  }
}
