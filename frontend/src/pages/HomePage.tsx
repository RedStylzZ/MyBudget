import Categories from "../components/Categories";
import Recent from "../components/Recent";
import './HomePage.scss'
import {useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {IPaymentController} from "../models/ControllerTypes";
import PaymentController from "../controllers/PaymentController";

export default function HomePage() {
    const token: string = useContext(AuthContext).token!
    const paymentController: IPaymentController = PaymentController()
    const [payments, setPayments] = useState()


    return (
        <div className={"homePage"}>
            <div className={"recentPayments"}>
                <Recent />
            </div>
            <div className={"homeCategories"}>
                {/*<Categories />*/}
            </div>
        </div>
    )
}