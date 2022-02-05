import CategoryItem from "./CategoryItem";
import {Category} from "../models/Category";
import {ITokenConfig} from "../models/Connection";

export default function Categories(props: {categories: Category[], config: ITokenConfig, getPayments: boolean}) {
    const {categories, config, getPayments} = props;
    if (!categories || !Array.isArray(categories)) return null;
    return (
        <>
            {
                categories.map((category, index) =>
                    <CategoryItem category={category} config={config} getPayments={getPayments} key={index}/>
                )
            }
        </>
    )
}