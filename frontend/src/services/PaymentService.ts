import {IPaymentController} from "../models/ControllerTypes";
import axios from "axios";
import {ITokenConfig} from "../models/Connection";

export default function PaymentService(config: ITokenConfig): IPaymentController {
    return {
        getPayments: (categoryID) => {
            return axios.get(`/api/payment/?categoryID=${categoryID}`, config).then(response => response.data)
        }
    }
}