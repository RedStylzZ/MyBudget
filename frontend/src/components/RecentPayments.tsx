import {Payment} from "../models/Payment";
import RecentPaymentItem from "./RecentPaymentItem";

export default function RecentPayments(props: { payments: Payment[] }) {
    const {payments} = props
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