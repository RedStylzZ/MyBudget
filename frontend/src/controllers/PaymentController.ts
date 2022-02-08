import {IPaymentController} from "../models/ControllerTypes";
import PaymentService from "../services/PaymentService";
import {ITokenConfig} from "../models/Connection";

export default function PaymentController(config: ITokenConfig | undefined): IPaymentController {

    const service: IPaymentController = PaymentService(config);

    return {
        getPayments: categoryId => {
            return service.getPayments(categoryId)
        },
        addPayment: payment => {
            return service.addPayment(payment)
        },
        deletePayment: (categoryID, paymentID) => {
            return service.deletePayment(categoryID, paymentID)
        },
        changePayment: (payment) => {
            console.log("Payment", payment)
            console.log("Config", config)
            return service.changePayment(payment)
        }
    }
}
