import {Category} from "../models/Category";
import MonetaryValue from "./MonetaryValue";

const mapToCategoryItem = (category: Category) => {
    return (
        <div className={"categoryItem"}>
            <h1>{category.categoryName}</h1>
            <h2>{<MonetaryValue amount={category.paymentSum}/>}</h2>
        </div>
    )
}

export default function HomeCategoryItem(props: { category: Category }) {
    const {category} = props

    return (
        <div className={"categoryItemCard"} id={category.categoryID}>
            {mapToCategoryItem(category)}
        </div>
    )
}