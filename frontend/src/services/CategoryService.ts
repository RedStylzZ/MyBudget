import axios from "axios";
import {ITokenConfig} from "../models/Connection";
import {ICategoryController} from "../models/Category";

export default function CategoryService(config: ITokenConfig | undefined): ICategoryController {
    const URL: string = "/api/category"
    return {
        getCategories: () => {
            return axios.get(URL, config).then(response => response.data)
        },
        addCategory: categoryName => {
            return axios.post(URL, {categoryName}, config).then(response => response.data)
        },
        deleteCategory: categoryID => {
            return axios.delete(URL + `/?categoryID=${categoryID}`, config).then(response => response.data)
        },
        renameCategory: category => {
            return axios.patch(URL, category, config).then(response => response.data)
        }
    }
}