import CategoryItem from "./CategoryItem";
import './Categories.scss'

export default function Categories() {

    return (
        <div className={"categories"}>
            <h1>Categories</h1>
            <CategoryItem />
        </div>
    )
}