import Categories from "../components/Categories";
import {useContext, useEffect, useState} from "react";
import {ICategoryController} from "../models/ControllerTypes";
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";
import {AuthContext} from "../context/AuthProvider";

export default function CategoryPage() {
    const token: string = useContext(AuthContext).token!
    const config = {headers: {Authorization: `Bearer ${token}`}}
    const controller: ICategoryController = CategoryController(config);
    const [categories, setCategories] = useState<Category[]>([])

    useEffect(() => {
        controller.getCategories().then(setCategories)
    }, [])

    return (
        <div className={"categoryPage"}>
            <h1>Categories</h1>
            <div className={"categories"}>
                <Categories categories={categories}/>
            </div>
        </div>
    )
}