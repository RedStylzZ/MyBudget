import {ICategoryController} from "../models/ControllerTypes";
import axios from "axios";

export default function CategoryService(config: { headers: { Authorization: string } }): ICategoryController {

    return {
        getCategories: () => {
            return axios.get("/api/category", config).then(response => response.data);
        }
    }
}