import PaymentService from "../services/PaymentService";
import {ITokenConfig} from "../models/Connection";
import {IPaymentController} from "../models/Payment";

export default function PaymentController(config: ITokenConfig | undefined): IPaymentController {

    const service: IPaymentController = PaymentService(config);

    return {
        getPayment: ((categoryId, paymentId) => {
            return service.getPayment(categoryId, paymentId)
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
        deletePayment: (categoryId, paymentId) => {
            return service.deletePayment(categoryId, paymentId)
        },
        changePayment: (payment) => {
            return service.changePayment(payment)
        }
    }
}
