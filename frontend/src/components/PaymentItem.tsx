import {IPayment} from "../models/IPayment";
import moment from 'moment';
import MonetaryValue from "./MonetaryValue";

interface PaymentItemProps {
    payment: IPayment
    deletePayment: (paymentID: string) => void
}

export default function PaymentItem({ payment, deletePayment }: PaymentItemProps) {
    const date: string = moment(payment.payDate).format('DD.MM.YYYY')
    return (
        <div className={"paymentItem"} id={payment.paymentID}>
            <h1>{payment.description}</h1>
            <h2><MonetaryValue amount={payment.amount}/></h2>
            <h3>{date}</h3>
            <input type="button" value={"Delete Payment"} onClick={() => deletePayment(payment.paymentID)}/>
        </div>
    )
}