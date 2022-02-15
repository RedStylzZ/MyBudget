import './HomePage.scss'
import {useContext, useEffect, useMemo, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import CategoryController from "../controllers/CategoryController";
import {Category, ICategoryController} from "../models/Category";
import HomeCategories from "../components/categories/HomeCategories";
import PaymentController from "../controllers/PaymentController";
import {IPaymentController, Payment} from "../models/Payment";
import RecentPayments from "../components/payments/RecentPayments";
import PieChart from "../components/PieChart";
import {Deposit, IDepositController} from "../models/Deposit";
import DepositController from "../controllers/DepositController";

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
    const depositController: IDepositController = useMemo(() => DepositController(config), [config])
    const [categories, setCategories] = useState<Category[]>([])
    const [payments, setPayments] = useState<Payment[]>([])
    const [deposits, setDeposits] = useState<Deposit[]>([])

    useEffect(() => {
        categoryController.getCategories().then((response) => {
            setCategories(response.filter((category) => !!category.paymentSum))
        })
        depositController.getDeposits().then(setDeposits)
        paymentController.getLastPayments().then(setPayments)
    }, [categoryController, paymentController, depositController])

    return (
        <div className={"homePage"}>
            <div className={"recentPayments"}>
                <h1>Recent Payments</h1>
                <RecentPayments payments={payments}/>
            </div>
            <div className={"homeCategories"}>
                <h1>Categories</h1>
                <h2></h2>
                <HomeCategories categories={categories}/>
            </div>
            <div className={"pieChart"}>
                <PieChart data={mapCategoriesToPieChartData(categories)}/>
            </div>
        </div>
    )
}
