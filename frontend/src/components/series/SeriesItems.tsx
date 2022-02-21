import {DepositSeries, PaymentSeries} from "../../models/Series";
import SeriesCard from "./SeriesCard";

interface ISeriesProps {
    paymentSeries: PaymentSeries[]
    depositSeries: DepositSeries[]
    deleteSeries: (seriesId: string | undefined, type: string) => void
}

export default function SeriesItems({paymentSeries, depositSeries, deleteSeries}: ISeriesProps) {

    return (
        <div className={"seriesLibrary"}>
            <h1>Payments</h1>
            <div className={"seriesItemCards"}>
                {
                    paymentSeries.map((seriesObj, index) =>
                        <SeriesCard series={seriesObj} deleteSeries={deleteSeries} key={index}/>
                    )
                }
            </div>
            <h1>Deposits</h1>
            <div className={"seriesItemCards"}>
                {
                    depositSeries.map((seriesObj, index) =>
                        <SeriesCard series={seriesObj} deleteSeries={deleteSeries} key={index}/>
                    )
                }
            </div>
        </div>
    )
}