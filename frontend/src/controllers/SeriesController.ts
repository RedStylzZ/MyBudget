import SeriesService from "../services/SeriesService";
import {ISeriesController, PaymentSeries} from "../models/Series";
import {ITokenConfig} from "../models/Connection";

export default function SeriesController(config: ITokenConfig | undefined): ISeriesController {

    const service: ISeriesController = SeriesService(config)

    return {
        getPaymentSeries: () => {
            return service.getPaymentSeries()
        },
        addPaymentSeries: (series: PaymentSeries) => {
            return service.addPaymentSeries(series)
        },
        deletePaymentSeries: seriesId => {
            return service.deletePaymentSeries(seriesId)
        },
        getDepositSeries: () => {
            return service.getDepositSeries()
        },
        addDepositSeries: series => {
            return service.addDepositSeries(series)
        },
        deleteDepositSeries: seriesId => {
            return service.deleteDepositSeries(seriesId)
        }
    }
}