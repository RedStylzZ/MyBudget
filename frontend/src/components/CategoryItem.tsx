import {Category} from "../models/Category";
import PaymentController from "../controllers/PaymentController";
import {ITokenConfig} from "../models/Connection";
import {useEffect, useState} from "react";
import {Payment} from "../models/IPayment";
import Payments from "./Payments";

const mapToCategoryItem = (category: Category) => {
    return (
        <div className={"categoryItem"}>
            <h1>{category.categoryName}</h1>
            <h2>{category.paymentSum + "€"}</h2>
        </div>
    )
}

const mapPayments = (payments: Payment[]) => {
    return (
        <div className={"payments"}>
            <h1>Payments</h1>
            <Payments payments={payments}/>
        </div>
    )
}

export default function CategoryItem(props: { category: Category, config: ITokenConfig, getPayments: boolean }) {
    const {category, config, getPayments} = props
    const controller = PaymentController(config)
    const [payments, setPayments] = useState<Payment[]>()

    useEffect(() => {
        getPayments && controller.getPayments(category.categoryID).then(setPayments)
        //eslint-disable-next-line
    }, [])


    return (
        <div className={"categoryItemCard"} id={category.categoryID}>
            {mapToCategoryItem(category)}
            {getPayments && mapPayments(payments!)}
        </div>
    )
}