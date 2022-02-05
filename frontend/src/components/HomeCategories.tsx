import {Category, IDeleteCategory} from "../models/Category";
import {ITokenConfig} from "../models/Connection";
import HomeCategoryItem from "./HomeCategoryItem";

export default function HomeCategories(props: {categories: Category[], config: ITokenConfig, deleteCategory?: IDeleteCategory, getPayments: boolean}) {
    const {categories} = props;
    if (!categories || !Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <HomeCategoryItem category={category} key={index}/>
                )
            }
        </>
    )
}