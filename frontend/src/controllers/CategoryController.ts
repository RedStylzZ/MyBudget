import CategoryService from "../services/CategoryService";
import {ITokenConfig} from "../models/Connection";
import {ICategoryController} from "../models/Category";

export default function CategoryController(config: ITokenConfig | undefined): ICategoryController {
    const service: ICategoryController = CategoryService(config);

    return {
        getCategories: () => {
            return service.getCategories()
        },
        addCategory: categoryName => {
            return service.addCategory(categoryName)
        },
        deleteCategory: categoryId => {
            return service.deleteCategory(categoryId)
        },
        renameCategory: category => {
            return service.renameCategory(category)
        }
    }

}