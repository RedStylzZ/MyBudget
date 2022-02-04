import {Payment} from "../models/IPayment";
import PaymentItem from "./PaymentItem";

export default function Payments(props: { payments: Payment[] }) {
    const {payments} = props
    if (!payments || !Array.isArray(payments)) return null;
    return (
        <>
            {
                payments.map((payment, index) =>
                    <PaymentItem payment={payment} key={index}/>
                )
            }
        </>
    )
}