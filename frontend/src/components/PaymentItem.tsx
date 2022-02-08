import {IPayment} from "../models/IPayment";
import moment from 'moment';
import MonetaryValue from "./MonetaryValue";
import {useNavigate} from "react-router-dom";

interface PaymentItemProps {
    payment: IPayment
    deletePayment: (paymentID: string) => void
    categoryID: string
}

export default function PaymentItem({payment, deletePayment, categoryID}: PaymentItemProps) {
    const date: string = moment(payment.payDate).format('DD.MM.YYYY')
    const navigate = useNavigate()
    return (
        <div className={"paymentItem"} id={payment.paymentID}>
            <h1>{payment.description}</h1>
            <h2><MonetaryValue amount={payment.amount}/></h2>
            <h3>{date}</h3>
            <input type="button" value={"Delete Payment"} onClick={() => deletePayment(payment.paymentID)}/>
            <input type="button" value={"Change Payment"}
                   onClick={() => navigate(`/changePayment/${categoryID}/${payment.paymentID}`)}/>
        </div>
    )
}