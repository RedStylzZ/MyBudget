import {Series} from "../models/Series";
import SeriesItem from "./SeriesItem";

interface ISeriesProps {
    series: Series[]
}

export default function SeriesItems({series}: ISeriesProps) {

    return (
        <div className={"SeriesItems"}>
            {
                series.map((seriesObj, index) =>
                    <SeriesItem series={seriesObj} key={index}/>
                )
            }
        </div>
    )
}