import axios from "axios";
import {Series} from "../models/Series";
import {ISeriesController} from "../models/ControllerTypes";
import {ITokenConfig} from "../models/Connection";

export default function SeriesService(config: ITokenConfig | undefined): ISeriesController {

    return {
        getSeries: () => {
            return axios.get(`/api/series`, config).then(response => response.data)
        },
        addSeries: (series: Series) => {
            return axios.post(`/api/series`, series, config).then(response => response.data)
        }
    }
}