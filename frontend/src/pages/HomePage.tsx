import './HomePage.scss'
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {ICategoryController} from "../models/ControllerTypes";
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";
import HomeCategories from "../components/HomeCategories";

export default function HomePage() {
    const config = useContext(AuthContext).config!
    const categoryController: ICategoryController = CategoryController(config)
    const [categories, setCategories] = useState<Category[]>([])

    useEffect(() => {
        if (!config) return
        categoryController.getCategories().then(setCategories)
        //eslint-disable-next-line
    }, [config])

    return (
        <div className={"homePage"}>
            <div className={"recentPayments"}>

            </div>
            <div className={"homeCategories"}>
                <h1>Categories</h1>
                <HomeCategories categories={categories} config={config} getPayments={false}/>
            </div>
        </div>
    )
}