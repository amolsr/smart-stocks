export class StockDetailsModel {
  constructor(
    public shortName: string,
    public longName: string,
    public exchange: string,
    public symbol: string,
    public sector: string,
    public industry: string,
    public currentPrice: {fmt:string},
    public change: {fmt:string},
    public changePercentage: {fmt:string},
    public previousClose: {fmt:string},
    public open: {fmt:string},
    public dayHigh: {fmt:string},
    public dayLow: {fmt:string},
    public fiftyTwoWeekLow: {fmt:string},
    public fiftyTwoWeekHigh: {fmt:string},
    public forwardPE: {fmt:string},
    public volume: {fmt:string},
    public longBusinessSummary: string,
    public marketCap: {fmt:string},
    public founded: string,
    public enterpriseValue: string,
    public priceToBook: {fmt:string},
  ) {}


}
