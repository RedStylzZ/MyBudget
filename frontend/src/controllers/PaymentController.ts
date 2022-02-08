import {IPaymentController} from "../models/ControllerTypes";
import PaymentService from "../services/PaymentService";
import {ITokenConfig} from "../models/Connection";

export default function PaymentController(config: ITokenConfig | undefined): IPaymentController {

    const service: IPaymentController = PaymentService(config);

    return {
        getPayment: ((categoryID, paymentID) => {
           return service.getPayment(categoryID, paymentID)
        }),
        getPayments: categoryId => {
            return service.getPayments(categoryId)
        },
        getLastPayments: () => {
            return service.getLastPayments()
        },
        addPayment: payment => {
            return service.addPayment(payment)
        },
        deletePayment: (categoryID, paymentID) => {
            return service.deletePayment(categoryID, paymentID)
        },
        changePayment: (payment) => {
            return service.changePayment(payment)
        }
    }
}
