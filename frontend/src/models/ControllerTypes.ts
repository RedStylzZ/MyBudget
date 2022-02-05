import {Payment} from "./IPayment";
import {Category} from "./Category";

export interface ILoginController {
    login: (username: string, password: string) => Promise<string>
}

export interface IPaymentController {
    getPayments: (categoryId: string) => Promise<Payment[]>
}

export interface ICategoryController {
    getCategories: () => Promise<Category[]>
    addCategory: (categoryName: string) => Promise<Category[]>
}