import {Series} from "../../models/Series";
import SeriesCard from "./SeriesCard";

interface ISeriesProps {
    series: Series[]
    deleteSeries: (seriesId?: string) => void
}

export default function SeriesItems({series, deleteSeries}: ISeriesProps) {

    return (
        <div className={"seriesItems"}>
            {
                series.map((seriesObj, index) =>
                    <SeriesCard series={seriesObj} deleteSeries={deleteSeries} key={index}/>
                )
            }
        </div>
    )
}