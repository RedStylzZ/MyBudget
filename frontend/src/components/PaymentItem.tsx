import {IPayment} from "../models/IPayment";
import moment from 'moment';

export default function PaymentItem(props: { payment: IPayment }) {
    const {payment} = props
    const date: string = moment(payment.payDate).format('DD.MM.YYYY')
    return (
        <div className={"paymentItem"} id={payment.paymentID}>
            <h1>{payment.description}</h1>
            <h2>{payment.amount + "€"}</h2>
            <h3>{date}</h3>
        </div>
    )
}