import {Category} from "../models/Category";
import PaymentController from "../controllers/PaymentController";
import {ITokenConfig} from "../models/Connection";
import {useEffect, useState} from "react";
import {Payment} from "../models/IPayment";
import Payments from "./Payments";

export default function CategoryItem(props: { category: Category, config: ITokenConfig }) {
    const {category, config} = props
    const controller = PaymentController(config)
    const [payments, setPayments] = useState<Payment[]>([])

    useEffect(() => {
        controller.getPayments(category.categoryID).then(setPayments)
        //eslint-disable-next-line
    }, [])

    return (
        <div className={"categoryItemCard"} id={category.categoryID}>
            <div className={"categoryItem"}>
                <h1>{category.categoryName}</h1>
                <h2>{category.paymentSum}</h2>
            </div>
            <h1>Payments</h1>
            <div className={"payments"}>
                <Payments payments={payments}/>
            </div>
        </div>
    )
}