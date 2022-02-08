import {Category, IDeleteCategory} from "../models/Category";
import PaymentController from "../controllers/PaymentController";
import {ITokenConfig} from "../models/Connection";
import {useEffect, useMemo, useState} from "react";
import {IPayment} from "../models/IPayment";
import Payments from "./Payments";
import {IPaymentController} from "../models/ControllerTypes";


interface CategoryItemProps {
    category: Category,
    config: ITokenConfig,
    deleteCategory: IDeleteCategory
}

const mapToCategoryItem = (category: Category, categorySum: number, deleteCategory: IDeleteCategory) => {
    return (
        <div className={"categoryItem"}>
            <h1>{category.categoryName}</h1>
            <h2>{categorySum + "€"}</h2>
            <input type="button" value={"Remove"} onClick={() => deleteCategory(category.categoryID)}/>
        </div>
    )
}

const calcCategorySum = (payments: IPayment[]) => {
    if (payments.length <= 0) return 0
    return payments.map(payment => payment.amount).reduce((a, b) => a + b)
}

export default function CategoryItem({category, config, deleteCategory}: CategoryItemProps) {
    const controller: IPaymentController = useMemo(() => PaymentController(config), [config])
    const [payments, setPayments] = useState<IPayment[]>([])
    const [categorySum, setCategorySum] = useState<number>(0)

    useEffect(() => {
        controller.getPayments(category.categoryID).then((response) => {
            setPayments(response)
        })
    }, [category.categoryID, controller])

    useEffect(() => {
        setCategorySum(calcCategorySum(payments))
    }, [payments])

    return (
        <div className={"categoryItemCard"} id={category.categoryID}>
            {mapToCategoryItem(category, categorySum, deleteCategory)}
            <Payments payments={payments} categoryID={category.categoryID} setPayments={setPayments}
                      controller={controller}/>
        </div>
    )
}