import axios from "axios";
import {DepositSeries, ISeriesController, PaymentSeries} from "../models/Series";
import {ITokenConfig} from "../models/Connection";

export default function SeriesService(config: ITokenConfig | undefined): ISeriesController {

    return {
        getPaymentSeries: () => {
            return axios.get(`/api/series/payment`, config).then(response => response.data)
        },
        addPaymentSeries: (series: PaymentSeries) => {
            return axios.post(`/api/series/payment`, series, config).then(response => response.data)
        },
        deletePaymentSeries: seriesId => {
            return axios.delete(`/api/series/payment?seriesId=${seriesId}`, config).then(response => response.data)
        },
        getDepositSeries: () => {
            return axios.get(`/api/series/deposit`, config).then(response => response.data)
        },
        addDepositSeries: (series: DepositSeries) => {
            return axios.post(`/api/series/deposit`, series, config).then(response => response.data)
        },
        deleteDepositSeries: seriesId => {
            return axios.delete(`/api/series/deposit?seriesId=${seriesId}`, config).then(response => response.data)
        }
    }
}