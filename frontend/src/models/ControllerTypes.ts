import {Payment} from "./IPayment";
import {Category} from "./Category";

export interface ILoginController {
    login: (username: string, password: string) => Promise<string>
    checkLoggedIn: () => Promise<boolean>
}

export interface IPaymentController {
    getPayments: (categoryId: string) => Promise<Payment[]>
}

export interface ICategoryController {
    getCategories: () => Promise<Category[]>
    addCategory: (categoryName: string) => Promise<Category[]>
    deleteCategory: (categoryID: string) => Promise<Category[]>
}