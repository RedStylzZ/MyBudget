import {PaymentDTO} from "./Payment";
import {DepositDTO} from "./Deposit";

export interface PaymentSeries {
    seriesId?: string
    startDate?: Date | null
    endDate?: Date | null
    scheduledDate: number
    payment: PaymentDTO
}

export interface DepositSeries {
    seriesId?: string
    startDate?: Date | null
    endDate?: Date | null
    scheduledDate: number
    deposit: DepositDTO
}

export interface ISeriesController {
    getPaymentSeries: () => Promise<PaymentSeries[]>
    addPaymentSeries: (series: PaymentSeries) => Promise<PaymentSeries[]>
    deletePaymentSeries: (seriesId: string) => Promise<PaymentSeries[]>
    getDepositSeries: () => Promise<DepositSeries[]>
    addDepositSeries: (series: DepositSeries) => Promise<DepositSeries[]>
    deleteDepositSeries: (seriesId: string) => Promise<DepositSeries[]>
}
