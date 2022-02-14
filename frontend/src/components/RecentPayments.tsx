import {Payment} from "../models/Payment";
import RecentPaymentItem from "./RecentPaymentItem";
import moment from "moment/moment";

type IDatePayment = {
    [n: string]: [Payment]
}

export default function RecentPayments(props: { payments: Payment[] }) {
    const {payments} = props
    const datePayments: IDatePayment = {}
    payments.forEach(payment => {
        if (!datePayments[payment.payDate.toString()]) {
            datePayments[payment.payDate.toString()] = [payment]
        } else {
            datePayments[payment.payDate.toString()].push(payment)
        }
    })

    return (
        <>
            {
                Object.entries(datePayments).map(((value, index) => {
                    const date: Date = new Date(value[0])
                    const dateString: string = moment(date).format('DD.MM.YYYY')
                    return (
                        <div className={"paymentDateWrap"} key={index}>
                            <h2 key={index}>{dateString}</h2>
                            <div className={"recentPaymentItems"}>
                                {
                                    value[1].map((payment, paymentIndex) =>
                                        <RecentPaymentItem payment={payment} key={paymentIndex}/>
                                    )
                                }
                            </div>
                        </div>
                    )
                }))
            }
        </>
    )
}