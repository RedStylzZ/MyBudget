import {Category, IDeleteCategory} from "../../models/Category";
import PaymentController from "../../controllers/PaymentController";
import {ITokenConfig} from "../../models/Connection";
import {useEffect, useMemo, useState} from "react";
import {IPaymentController, Payment} from "../../models/Payment";
import Payments from "../payments/Payments";
import MonetaryValue from "../MonetaryValue";
import {NavigateFunction, useNavigate} from "react-router-dom";
import Button from "../Button";


interface CategoryItemProps {
    category: Category,
    config: ITokenConfig,
    deleteCategory: IDeleteCategory
}

const mapToCategoryItem = (category: Category, categorySum: number, deleteCategory: IDeleteCategory, navigate: NavigateFunction) => {
    return (
        <div className={"categoryItem"}>
            <h1>{category.categoryName}</h1>
            <h2><MonetaryValue amount={categorySum}/></h2>
            <div>
                <Button value={"Remove"} onClick={() => deleteCategory(category.categoryId)}/>
                <Button value={"Rename"}
                        onClick={() => navigate(`/renameCategory/${category.categoryId}/${category.categoryName}`)}/>
            </div>
        </div>
    )
}

const calcCategorySum = (payments: Payment[]) => {
    if (payments.length <= 0) return 0
    return payments.map(payment => payment.amount).reduce((a, b) => a + b)
}

export default function CategoryItem({category, config, deleteCategory}: CategoryItemProps) {
    const controller: IPaymentController = useMemo(() => PaymentController(config), [config])
    const [payments, setPayments] = useState<Payment[]>([])
    const [categorySum, setCategorySum] = useState<number>(0)
    const navigate = useNavigate()

    useEffect(() => {
        controller.getPayments(category.categoryId).then((response) => {
            setPayments(response)
        })
    }, [category.categoryId, controller])

    useEffect(() => {
        setCategorySum(calcCategorySum(payments))
    }, [payments])

    return (
        <div className={"categoryItemCard"} id={category.categoryId}>
            {mapToCategoryItem(category, categorySum, deleteCategory, navigate)}
            <Payments payments={payments} categoryId={category.categoryId} setPayments={setPayments}
                      controller={controller}/>
        </div>
    )
}