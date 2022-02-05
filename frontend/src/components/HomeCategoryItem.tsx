import {Category} from "../models/Category";

const mapToCategoryItem = (category: Category) => {
    return (
        <div className={"categoryItem"}>
            <h1>{category.categoryName}</h1>
            <h2>{category.paymentSum + "€"}</h2>
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