import {Payment} from "./Payment";
import {Category} from "./Category";
import {ITokenConfig} from "./Connection";

export interface ILoginController {
    login: (username: string, password: string) => Promise<string>
    checkLoggedIn: () => boolean
    isAdmin: (config: ITokenConfig) => Promise<boolean>
}

export interface ILoginService {
    login: (username: string, password: string) => Promise<string>
    isAdmin: (config: ITokenConfig) => Promise<boolean>
}

export interface IPaymentController {
    getPayment: (categoryID: string, paymentID: string) => Promise<Payment>
    getPayments: (categoryID: string) => Promise<Payment[]>
    getLastPayments: () => Promise<Payment[]>
    addPayment: (payment: Payment) => Promise<Payment[]>
    deletePayment: (categoryID: string, paymentID: string) => Promise<Payment[]>
    changePayment: (payment: Payment) => Promise<Payment[]>
}

export interface ICategoryController {
    getCategories: () => Promise<Category[]>
    addCategory: (categoryName: string) => Promise<Category[]>
    deleteCategory: (categoryID: string) => Promise<Category[]>
}
