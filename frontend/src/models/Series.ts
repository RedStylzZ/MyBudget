import {PaymentDTO} from "./Payment";

export interface Series {
    seriesId?: string
    startDate?: Date | null
    endDate?: Date | null
    scheduledDate: number
    payment: PaymentDTO
}