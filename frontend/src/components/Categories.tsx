import CategoryItem from "./CategoryItem";
import {Category, IDeleteCategory} from "../models/Category";
import {ITokenConfig} from "../models/Connection";

export default function Categories(props: {categories: Category[], config: ITokenConfig, deleteCategory?: IDeleteCategory, getPayments: boolean}) {
    const {categories, config, deleteCategory, getPayments} = props;
    if (!categories || !Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <CategoryItem category={category} config={config} deleteCategory={deleteCategory!} getPayments={getPayments} key={index}/>
                )
            }
        </>
    )
}