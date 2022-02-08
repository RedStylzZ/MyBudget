import {IPayment} from "./IPayment";
import {Category} from "./Category";

export interface ILoginController {
    login: (username: string, password: string) => Promise<string>
    checkLoggedIn: () => boolean
}

export interface ILoginService {
    login: (username: string, password: string) => Promise<string>
}

export interface IPaymentController {
    getPayments: (categoryId: string) => Promise<IPayment[]>
    addPayment: (payment: IPayment) => Promise<IPayment[]>
    deletePayment: (categoryID: string, paymentID: string) => Promise<IPayment[]>
    changePayment: (payment: IPayment) => Promise<IPayment[]>
}

export interface ICategoryController {
    getCategories: () => Promise<Category[]>
    addCategory: (categoryName: string) => Promise<Category[]>
    deleteCategory: (categoryID: string) => Promise<Category[]>
}