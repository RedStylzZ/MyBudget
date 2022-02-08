import Categories from "../components/Categories";
import {ChangeEvent, FormEventHandler, useContext, useEffect, useState} from "react";
import {ICategoryController} from "../models/ControllerTypes";
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";
import {AuthContext} from "../context/AuthProvider";
import './CategoryPage.scss'

export default function CategoryPage() {
    const config = useContext(AuthContext).config!
    const controller: ICategoryController = CategoryController(config);
    const [categories, setCategories] = useState<Category[]>([])
    const [categoryInput, setCategoryInput] = useState<string>("")

    useEffect(() => {
        if (!config) return
        controller.getCategories().then(setCategories)
        //eslint-disable-next-line
    }, [config])

    const addCategory: FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault()
        const categoryName: string = categoryInput.trim()

        if (categoryName && categoryName.length > 0) {
            controller.addCategory(categoryName).then(setCategories)
        }
        setCategoryInput("")
    }

    const deleteCategory = (categoryID: string) => {
        if (!categoryID && categoryID.length <= 1) return
        controller.deleteCategory(categoryID).then(setCategories)
    }

    const onCategoryInputChange = (event: ChangeEvent<HTMLInputElement>) =>
        setCategoryInput(event.target.value)

    return (
        <div className={"categoryPage"}>
            <div className={"categoryHeader"}>
                <h1>Categories</h1>
            </div>
            <div className={"categories"}>
                <form onSubmit={addCategory} className={"addCategory"}>
                    <input type="text" id="categoryInput" onChange={onCategoryInputChange} placeholder={"Category name"}
                           value={categoryInput}/>
                    <input type="submit" value={"Add category"}/>
                </form>
                <Categories categories={categories} config={config} deleteCategory={deleteCategory}/>
            </div>
        </div>
    )
}