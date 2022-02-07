import {Payment} from "../models/IPayment";
import PaymentItem from "./PaymentItem";

interface PaymentsProps {
    payments: Payment[]
}

export default function Payments({ payments }: PaymentsProps) {
    if (!Array.isArray(payments)) return null;
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