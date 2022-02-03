import {IPaymentController} from "../models/ControllerTypes";
import axios from "axios";

export default function PaymentService(): IPaymentController {
    return {
        getPayments: () => {
            return axios.get("/api/payment").then(response => response.data)
        }
    }
}