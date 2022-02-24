import Categories from "../components/categories/Categories";
import {ChangeEvent, FormEventHandler, useContext, useEffect, useMemo, useState} from "react";
import CategoryController from "../controllers/CategoryController";
import {Category, ICategoryController} from "../models/Category";
import {AuthContext} from "../context/AuthProvider";
import './CategoryPage.scss'
import Button from "../components/Button";
import InputBox from "../components/InputBox";
import {DataContext} from "../context/DataProvider";

export default function CategoryPage() {
    const config = useContext(AuthContext).config!
    const {setCurrentPage} = useContext(DataContext)
    const controller: ICategoryController = useMemo(() => CategoryController(config), [config]);
    const [categories, setCategories] = useState<Category[]>([])
    const [categoryInput, setCategoryInput] = useState<string>("")

    useEffect(() => {
        controller.getCategories().then(setCategories)
    }, [controller])

    useEffect(() => {
        setCurrentPage("Categories")
    }, [setCurrentPage])

    const addCategory: FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault()
        const categoryName: string = categoryInput.trim()

        if (categoryName && categoryName.length > 0) {
            controller.addCategory(categoryName).then(setCategories)
        }
        setCategoryInput("")
    }

    const deleteCategory = (categoryId: string) => {
        if (!categoryId && categoryId.length <= 1) return
        controller.deleteCategory(categoryId).then(setCategories)
    }

    const onCategoryInputChange = (event: ChangeEvent<HTMLInputElement>) =>
        setCategoryInput(event.target.value)

    return (
        <div className={"categoryPage"}>
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
