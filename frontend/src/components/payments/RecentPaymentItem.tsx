import {Payment} from "../../models/Payment";
import MonetaryValue from "../MonetaryValue";

export default function RecentPaymentItem(props: { payment: Payment }) {
    const {payment} = props
    return (
        <div className={"recentPaymentItemCard"}>
            <h1>{payment.description}</h1>
            <h2>{<MonetaryValue amount={payment.amount}/>}</h2>
        </div>
    )
}