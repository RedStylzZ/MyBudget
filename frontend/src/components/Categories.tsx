import CategoryItem from "./CategoryItem";
import {Category, IDeleteCategory} from "../models/Category";
import {ITokenConfig} from "../models/Connection";

export default function Categories(props: { categories: Category[], config: ITokenConfig, deleteCategory: IDeleteCategory }) {
    const {categories, config, deleteCategory} = props;
    if (!categories || !Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <CategoryItem category={category} config={config} deleteCategory={deleteCategory!} key={index}/>
                )
            }
        </>
    )
}