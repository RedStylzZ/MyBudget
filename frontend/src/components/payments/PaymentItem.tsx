import {Payment} from "../../models/Payment";
import MonetaryValue from "../MonetaryValue";
import {useNavigate} from "react-router-dom";
import FormatDate from "../FormatDate";
import Button from "../Button";

interface PaymentItemProps {
    payment: Payment
    deletePayment: (paymentId: string) => void
    categoryId: string
}

export default function PaymentItem({payment, deletePayment, categoryId}: PaymentItemProps) {
    const navigate = useNavigate()
    return (
        <div className={"paymentItem"} id={payment.paymentId}>
            <h1>{payment.description}</h1>
            <h2><MonetaryValue amount={payment.amount}/></h2>
            <h3><FormatDate date={payment.payDate}/></h3>
            <Button value={"Delete Payment"} onClick={() => deletePayment(payment.paymentId)}/>
            <Button value={"Change Payment"}
                    onClick={() => navigate(`/changePayment/${categoryId}/${payment.paymentId}`)}/>
        </div>
    )
}