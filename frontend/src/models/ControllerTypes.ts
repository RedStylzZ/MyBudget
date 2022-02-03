import {Payment} from "./IPayment";

export interface ILoginController {
    login: (username: string, password: string) => Promise<string>
}

export interface IPaymentController {
    getPayments: () => Promise<Payment>
}