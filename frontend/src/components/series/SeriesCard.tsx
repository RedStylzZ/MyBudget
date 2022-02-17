import {DepositSeries, PaymentSeries} from "../../models/Series";
import MonetaryValue from "../MonetaryValue";
import FormatDate from "../FormatDate";
import Button from "../Button";

interface ISeriesItemProps {
    series: PaymentSeries | DepositSeries
    deleteSeries: (seriesId: string | undefined, type: string) => void
}

export default function SeriesCard({series, deleteSeries}: ISeriesItemProps) {

    const amount: number = ("payment" in series && series.payment.amount) ||
        ("deposit" in series && series.deposit.amount)
        || 0

    const description: string = ("payment" in series && series.payment.description) ||
        ("deposit" in series && series.deposit.description)
        || ""

    const type: string = ("payment" in series && "payment") ||
        ("deposit" in series && "deposit")
        || ""

    console.log(series)

    return (
        <div className={"seriesItemCard"}>
            <Button onClick={() => deleteSeries(series.seriesId, type)} value={"Delete"}/>
            <div className={"seriesItem"}>
                <h2>Start</h2>
                <h3>{series.startDate ? <FormatDate date={series.startDate}/> : "Infinite"}</h3>
                <h2>End</h2>
                <h3>{series.endDate ? <FormatDate date={series.endDate}/> : "Infinite"}</h3>
                <h2>Scheduled Day</h2>
                <h3>{series.scheduledDate}</h3>
            </div>
            <div className={"paymentItem"}>
                <h2>{description}</h2>
                <MonetaryValue amount={amount}/>
            </div>
        </div>
    )
}