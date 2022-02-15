import {PaymentDTO} from "./Payment";

export interface Series {
    seriesId?: string
    startDate?: Date | null
    endDate?: Date | null
    scheduledDate: number
    payment: PaymentDTO
}

export interface ISeriesController {
    getSeries: () => Promise<Series[]>
    addSeries: (series: Series) => Promise<Series[]>
    deleteSeries: (seriesId: string) => Promise<Series[]>
}
