import {Category} from "../models/Category";

export default function CategoryItem(props: {category: Category}) {
    const {category} = props
    return (
        <div className={"categoryItem"} id={category.categoryID}>
            <h1>{category.categoryName}</h1>
            <h2>{category.paymentSum}</h2>
        </div>
    )
}