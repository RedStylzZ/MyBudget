import axios from "axios";
import {ITokenConfig} from "../models/Connection";
import {IPaymentController} from "../models/Payment";

export default function PaymentService(config: ITokenConfig | undefined): IPaymentController {
    return {
        getPayment: ((categoryID, paymentID) => {
            return axios.get(`/api/payment/?categoryID=${categoryID}&paymentID=${paymentID}`, config).then(response => response.data)
        }),
        getPayments: (categoryID) => {
            return axios.get(`/api/payment/${categoryID}`, config).then(response => response.data)
        },
        getLastPayments: () => {
            return axios.get("/api/payment", config).then(response => response.data)
        },
        addPayment: payment => {
            return axios.post("/api/payment", payment, config).then(response => response.data)
        },
        deletePayment: (categoryID, paymentID) => {
            return axios.delete(`/api/payment/?categoryID=${categoryID}&paymentID=${paymentID}`, config).then(response => response.data)
        },
        changePayment: (payment) => {
            return axios.put(`/api/payment`, payment, config).then(response => response.data)
        }
    }
}
