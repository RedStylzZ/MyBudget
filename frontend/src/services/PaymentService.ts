import axios from "axios";
import {ITokenConfig} from "../models/Connection";
import {IPaymentController} from "../models/Payment";

export default function PaymentService(config: ITokenConfig | undefined): IPaymentController {
    return {
        getPayment: ((categoryId, paymentId) => {
            return axios.get(`/api/payment/?categoryId=${categoryId}&paymentId=${paymentId}`, config).then(response => response.data)
        }),
        getPayments: (categoryId) => {
            return axios.get(`/api/payment/${categoryId}`, config).then(response => response.data)
        },
        getLastPayments: () => {
            return axios.get("/api/payment", config).then(response => response.data)
        },
        addPayment: payment => {
            return axios.post("/api/payment", payment, config).then(response => response.data)
        },
        deletePayment: (categoryId, paymentId) => {
            return axios.delete(`/api/payment/?categoryId=${categoryId}&paymentId=${paymentId}`, config).then(response => response.data)
        },
        changePayment: (payment) => {
            return axios.put(`/api/payment`, payment, config).then(response => response.data)
        }
    }
}
