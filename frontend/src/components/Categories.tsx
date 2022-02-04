import CategoryItem from "./CategoryItem";
import './Categories.scss'
import {Category} from "../models/Category";
import {ITokenConfig} from "../models/Connection";

export default function Categories(props: {categories: Category[], config: ITokenConfig}) {
    const {categories, config} = props;
    if (!categories || !Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <CategoryItem category={category} config={config} key={index}/>
                )
            }
        </>
    )
}