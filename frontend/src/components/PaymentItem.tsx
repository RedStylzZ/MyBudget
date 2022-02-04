import {Payment} from "../models/IPayment";

export default function PaymentItem(props: {payment: Payment}) {
    const {payment} = props

    return (
        <div className={"paymentItem"} id={payment.paymentID}>
            <h1>{payment.description}</h1>
            <h2>{payment.amount}</h2>
            <h3>{payment.payDate}</h3>
        </div>
    )
}