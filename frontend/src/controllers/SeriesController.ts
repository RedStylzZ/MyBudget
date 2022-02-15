import SeriesService from "../services/SeriesService";
import {ISeriesController, Series} from "../models/Series";
import {ITokenConfig} from "../models/Connection";

export default function SeriesController(config: ITokenConfig | undefined): ISeriesController {

    const service: ISeriesController = SeriesService(config)

    return {
        getSeries: () => {
            return service.getSeries()
        },
        addSeries: (series: Series) => {
            return service.addSeries(series)
        },
        deleteSeries: seriesId => {
            return service.deleteSeries(seriesId)
        }
    }
}