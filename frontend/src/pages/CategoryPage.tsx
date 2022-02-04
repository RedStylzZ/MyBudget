import Categories from "../components/Categories";
import {FormEventHandler, useContext, useEffect, useState} from "react";
import {ICategoryController} from "../models/ControllerTypes";
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";
import {AuthContext} from "../context/AuthProvider";

interface ITextInput {
    categoryInput: { value: string }
}

export default function CategoryPage() {
    const config = useContext(AuthContext).config!
    console.log(config)
    const controller: ICategoryController = CategoryController(config);
    const [categories, setCategories] = useState<Category[]>([])

    useEffect(() => {
        if (!config) return
        controller.getCategories().then(setCategories)
        //eslint-disable-next-line
    }, [config])

    const addCategory: FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault()
        const form = event.currentTarget
        const formElements = form.elements as typeof form.elements & ITextInput
        const category = formElements.categoryInput.value.trim()

        if (category && category.length > 0) {
            controller.getCategories().then(setCategories)
        }
    }

    return (
        <div className={"categoryPage"}>
            <div className={"categoryHeader"}>
                <h1>Categories</h1>
            </div>
            <div className={"categories"}>
                <form onSubmit={addCategory} className={"addCategory"}>
                    <input type="text" id="categoryInput"/>
                    <input type="submit" value={"Add category"}/>
                </form>
                <Categories categories={categories} config={config}/>
            </div>
        </div>
    )
}