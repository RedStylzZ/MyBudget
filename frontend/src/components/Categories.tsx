import CategoryItem from "./CategoryItem";
import {Category, IDeleteCategory} from "../models/Category";
import {ITokenConfig} from "../models/Connection";

interface CategoriesProps {
    categories: Category[],
    config: ITokenConfig,
    deleteCategory: IDeleteCategory
}

export default function Categories({ categories, config, deleteCategory }: CategoriesProps) {
    if (!Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <CategoryItem category={category} config={config} deleteCategory={deleteCategory} key={index}/>
                )
            }
        </>
    )
}