import {IPaymentController} from "../models/ControllerTypes";
import axios from "axios";
import {ITokenConfig} from "../models/Connection";

export default function PaymentService(config: ITokenConfig): IPaymentController {
    return {
        getPayments: (categoryID) => {
            return axios.get(`/api/payment/?categoryID=${categoryID}`, config).then(response => response.data)
        },
        addPayment: payment => {
            return axios.put("/api/payment", payment, config).then(response => response.data)
        },
        deletePayment: (categoryID, paymentID) => {
            config["data"] = {categoryID, paymentID}
            return axios.delete(`/api/payment`, config).then(response => response.data)
        }
    }
}
