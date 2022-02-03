import {IPaymentController} from "../models/ControllerTypes";
import PaymentService from "../services/PaymentService";

export default function PaymentController(): IPaymentController {

    const service: IPaymentController = PaymentService();

    return {
        getPayments: () => {
            return service.getPayments().then(response => response)
        }
    }
}