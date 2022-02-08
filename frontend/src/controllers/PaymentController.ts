import {IPaymentController} from "../models/ControllerTypes";
import PaymentService from "../services/PaymentService";
import {ITokenConfig} from "../models/Connection";

export default function PaymentController(config: ITokenConfig): IPaymentController {

    const service: IPaymentController = PaymentService(config);

    return {
        getPayments: categoryId => {
            return service.getPayments(categoryId)
        },
        addPayment: payment => {
            return service.addPayment(payment)
        },
        deletePayment: (categoryID, paymentID) => service.deletePayment(categoryID, paymentID)
    }
}
