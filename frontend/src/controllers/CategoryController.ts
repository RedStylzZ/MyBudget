import {ICategoryController} from "../models/ControllerTypes";
import CategoryService from "../services/CategoryService";
import {ITokenConfig} from "../models/Connection";

export default function CategoryController(config: ITokenConfig | undefined): ICategoryController {
    const service: ICategoryController = CategoryService(config);

    return {
        getCategories: () => {
            return service.getCategories()
        }
    }

}