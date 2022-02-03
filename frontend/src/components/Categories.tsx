import CategoryItem from "./CategoryItem";
import './Categories.scss'
import {Category} from "../models/Category";

export default function Categories(props: {categories: Category[]}) {
    const {categories} = props;
    if (!categories || !Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <CategoryItem category={category} key={index}/>
                )
            }
        </>
    )
}