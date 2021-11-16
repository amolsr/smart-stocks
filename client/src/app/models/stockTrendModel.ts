export class stockTrendModel {
  constructor(
    public period: string,
    public strongBuy: number,
    public buy: number,
    public hold: number,
    public sell: number,
    public strongSell: number,
  ) {}
}
