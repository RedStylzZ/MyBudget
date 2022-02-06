import {ICategoryController} from "../models/ControllerTypes";
import axios from "axios";
import {ITokenConfig} from "../models/Connection";

export default function CategoryService(config: ITokenConfig | undefined): ICategoryController {
    const URL: string = "/api/category"
    return {
        getCategories: () => {
            return axios.get(URL, config).then(response => response.data);
        },
        addCategory: categoryName => {
            return axios.put(URL, {categoryName}, config).then(response => response.data)
        },
        deleteCategory: categoryID => {
            config!.data = {categoryID}
            return axios.delete(URL, config).then(response => response.data)
        }
    }
}