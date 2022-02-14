import {Series} from "../models/Series";
import MonetaryValue from "./MonetaryValue";

interface ISeriesItemProps {
    series: Series
    deleteSeries: (seriesId?: string) => void
}

export default function SeriesItem({series, deleteSeries}: ISeriesItemProps) {

    return (
        <div className={"seriesItemCard"}>
            <input type="button" onClick={() => deleteSeries(series.seriesId)} value={"Delete"}/>
            <div className={"seriesItem"}>
                <h2>Start</h2>
                <h3>{series.startDate || "Infinite"}</h3>
                <h2>End</h2>
                <h3>{series.endDate || "Infinite"}</h3>
                <h2>Scheduled Day</h2>
                <h3>{series.scheduledDate}</h3>
            </div>
            <div className={"paymentItem"}>
                <h2>{series.payment.description}</h2>
                <MonetaryValue amount={series.payment.amount}/>
            </div>
        </div>
    )
}