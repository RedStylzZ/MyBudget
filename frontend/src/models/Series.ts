import {PaymentDTO} from "./Payment";

export interface Series {
    startDate?: Date
    endDate?: Date
    scheduledDate: number
    payment: PaymentDTO
}