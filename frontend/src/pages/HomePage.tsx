import Categories from "../components/Categories";
import Recent from "../components/Recent";
import './HomePage.scss'
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {ICategoryController, IPaymentController} from "../models/ControllerTypes";
import PaymentController from "../controllers/PaymentController";
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";

export default function HomePage() {
    const token: string = useContext(AuthContext).token!
    const config = {headers: {Authorization: `Bearer ${token}`}}
    const paymentController: IPaymentController = PaymentController()
    const categoryController: ICategoryController = CategoryController(config)
    const [payments, setPayments] = useState()
    const [categories, setCategories] = useState<Category[]>([])

    useEffect(() => {
        categoryController.getCategories().then(setCategories)
    }, [])

    return (
        <div className={"homePage"}>
            <div className={"recentPayments"}>
                <Recent />
            </div>
            <div className={"homeCategories"}>
                <h1>Categories</h1>
                <Categories categories={categories}/>
            </div>
        </div>
    )
}