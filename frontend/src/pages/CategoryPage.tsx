import Categories from "../components/categories/Categories";
import {ChangeEvent, FormEventHandler, useContext, useEffect, useMemo, useState} from "react";
import CategoryController from "../controllers/CategoryController";
import {Category, ICategoryController} from "../models/Category";
import {AuthContext} from "../context/AuthProvider";
import './CategoryPage.scss'
import Button from "../components/Button";
import InputBox from "../components/InputBox";

export default function CategoryPage() {
    const config = useContext(AuthContext).config!
    const controller: ICategoryController = useMemo(() => CategoryController(config), [config]);
    const [categories, setCategories] = useState<Category[]>([])
    const [categoryInput, setCategoryInput] = useState<string>("")

    useEffect(() => {
        controller.getCategories().then(setCategories)
    }, [controller])

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
                    <InputBox id={"categoryInput"} onChange={onCategoryInputChange} placeholder={"Category name"}/>
                    <Button value={"Add category"} type={"submit"}/>
                </form>
                <Categories categories={categories} config={config} deleteCategory={deleteCategory}/>
            </div>
        </div>
    )
}
