import {ICategoryController} from "../models/ControllerTypes";
import CategoryService from "../services/CategoryService";

export default function CategoryController(config: { headers: { Authorization: string } }): ICategoryController {
    const service: ICategoryController = CategoryService(config);

    return {
        getCategories: () => {
            return service.getCategories()
        }
    }

}