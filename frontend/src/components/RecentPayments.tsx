import {Payment} from "../models/Payment";
import RecentPaymentItem from "./RecentPaymentItem";

type IDatePayment = {
    [n: string]: [Payment]
}

export default function RecentPayments(props: { payments: Payment[] }) {
    const {payments} = props
    const datePayments: IDatePayment = {}

    payments.forEach(payment => {
        if (!datePayments[payment.payDate.toString()]){
            datePayments[payment.payDate.toString()] = [payment]
        } else {
            datePayments[payment.payDate.toString()].push(payment)
        }
    })
    console.log(datePayments)

    return (
        <>
            {
                payments.map((payment, index) =>
                    <RecentPaymentItem payment={payment} key={index}/>
                )
            }
        </>
    )
}