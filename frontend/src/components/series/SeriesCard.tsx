import {Series} from "../../models/Series";
import MonetaryValue from "../MonetaryValue";
import FormatDate from "../FormatDate";
import {Link} from "react-router-dom";

interface ISeriesItemProps {
    series: Series
    deleteSeries: (seriesId?: string) => void
}

export default function SeriesCard({series, deleteSeries}: ISeriesItemProps) {

    return (
        <div className={"seriesItemCard"}>
            <Link to={"#"}><span onClick={() => deleteSeries(series.seriesId)}>Delete</span></Link>
            <div className={"seriesItem"}>
                <h2>Start</h2>
                <h3>{series.startDate ? <FormatDate date={series.startDate}/> : "Infinite"}</h3>
                <h2>End</h2>
                <h3>{series.endDate ? <FormatDate date={series.endDate}/> : "Infinite"}</h3>
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