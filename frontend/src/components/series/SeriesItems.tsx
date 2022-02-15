import {Series} from "../../models/Series";
import SeriesItem from "./SeriesItem";

interface ISeriesProps {
    series: Series[]
    deleteSeries: (seriesId?: string) => void
}

export default function SeriesItems({series, deleteSeries}: ISeriesProps) {

    return (
        <div className={"seriesItems"}>
            {
                series.map((seriesObj, index) =>
                    <SeriesItem series={seriesObj} deleteSeries={deleteSeries} key={index}/>
                )
            }
        </div>
    )
}