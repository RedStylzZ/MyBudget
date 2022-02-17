import {Payment} from "../../models/Payment";
import MonetaryValue from "../MonetaryValue";
import {useNavigate} from "react-router-dom";
import FormatDate from "../FormatDate";
import Button from "../Button";

interface PaymentItemProps {
    payment: Payment
    deletePayment: (paymentID: string) => void
    categoryID: string
}

export default function PaymentItem({payment, deletePayment, categoryID}: PaymentItemProps) {
    const navigate = useNavigate()
    return (
        <div className={"paymentItem"} id={payment.paymentID}>
            <h1>{payment.description}</h1>
            <h2><MonetaryValue amount={payment.amount}/></h2>
            <h3><FormatDate date={payment.payDate}/></h3>
            <Button value={"Delete Payment"} onClick={() => deletePayment(payment.paymentID)}/>
            <Button value={"Change Payment"} onClick={() => navigate(`/changePayment/${categoryID}/${payment.paymentID}`)}/>
        </div>
    )
}