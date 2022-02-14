import {PaymentDTO} from "./Payment";

export interface Series {
    seriesId?: string
    startDate?: Date
    endDate?: Date
    scheduledDate: number
    payment: PaymentDTO
}