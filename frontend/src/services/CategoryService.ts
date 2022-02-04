import {ICategoryController} from "../models/ControllerTypes";
import axios from "axios";
import {ITokenConfig} from "../models/Connection";

export default function CategoryService(config: ITokenConfig | undefined): ICategoryController {

    return {
        getCategories: () => {
            return axios.get("/api/category", config).then(response => response.data);
        }
    }
}