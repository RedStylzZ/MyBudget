import './HomePage.scss'
import {useContext, useEffect, useMemo, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {ICategoryController, IPaymentController} from "../models/ControllerTypes";
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";
import HomeCategories from "../components/HomeCategories";
import PaymentController from "../controllers/PaymentController";
import {Payment} from "../models/Payment";
import RecentPayments from "../components/RecentPayments";
import PieChart from "../components/PieChart";

const mapCategoriesToPieChartData = (categories: Category[]) => {
    return categories.map(category => {
        return {
            id: category.categoryName,
            label: category.categoryName,
            value: category.paymentSum
        }
    })
}

export default function HomePage() {
    const config = useContext(AuthContext).config!
    const categoryController: ICategoryController = useMemo(() => CategoryController(config), [config])
    const paymentController: IPaymentController = useMemo(() => PaymentController(config), [config])
    const [categories, setCategories] = useState<Category[]>([])
    const [payments, setPayments] = useState<Payment[]>([])

    useEffect(() => {
        categoryController.getCategories().then((response) => {
            setCategories(response.filter((category) => !!category.paymentSum))
        })
        paymentController.getLastPayments().then(setPayments)
    }, [categoryController, paymentController])

    return (
        <div className={"homePage"}>
            <div className={"recentPayments"}>
                <h1>Payments</h1>
                <RecentPayments payments={payments}/>
            </div>
            <div className={"homeCategories"}>
                <h1>Categories</h1>
                <HomeCategories categories={categories}/>
            </div>
            <div className={"pieChart"}>
                <PieChart data={mapCategoriesToPieChartData(categories)}/>
            </div>
        </div>
    )
}