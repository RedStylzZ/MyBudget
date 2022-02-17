import {Category} from "../../models/Category";
import HomeCategoryItem from "./HomeCategoryItem";

export default function HomeCategories(props: { categories: Category[] }) {
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