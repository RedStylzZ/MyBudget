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
import {IDepositController} from "../models/Deposit";
import DepositController from "../controllers/DepositController";
import MonetaryValue from "../components/MonetaryValue";

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
    const [depositSum, setDepositSum] = useState<number>(0)
    const [availableMoney, setAvailableMoney] = useState<number>(depositSum)

    useEffect(() => {
        categoryController.getCategories().then((response) => {
            setCategories(response.filter((category) => !!category.paymentSum))
        })
        depositController.getLatestDeposits().then((deposits) => {
            if (deposits.length < 1) return
            const sum: number = deposits.map(deposit => deposit.amount).reduce((a, b) => a + b);
            setDepositSum(sum)
        })
        paymentController.getLastPayments().then(setPayments)
    }, [categoryController, paymentController, depositController])

    useEffect(() => {
        if (categories.length < 1) return
        const aMoney: number = depositSum - categories.map(category => category.paymentSum).reduce((a, b) => a! + b!)!;
        setAvailableMoney(aMoney)
    }, [categories, depositSum])

    return (
        <div className={"homePage"}>
            <div className={"recentPayments"}>
                <h1>Recent Payments</h1>
                <RecentPayments payments={payments}/>
            </div>
            <div className={"homeCategories"}>
                <div>
                    <h1>Categories</h1>
                    <h2>Deposit sum</h2>
                    <h3><MonetaryValue amount={depositSum}/></h3>
                    <h2>Available money</h2>
                    <h3><MonetaryValue amount={availableMoney}/></h3>
                </div>

                <HomeCategories categories={categories}/>
            </div>
            <div className={"pieChart"}>
                <PieChart data={mapCategoriesToPieChartData(categories)}/>
            </div>
        </div>
    )
}
