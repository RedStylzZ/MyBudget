export interface Payment {
    paymentID: string;
    categoryID: string;
    description: string;
    amount: number;
    payDate: Date;
}

export interface PaymentDTO {
    description: string;
    categoryID: string;
    amount: number;
}

export interface IPaymentController {
    getPayment: (categoryID: string, paymentID: string) => Promise<Payment>
    getPayments: (categoryID: string) => Promise<Payment[]>
    getLastPayments: () => Promise<Payment[]>
    addPayment: (payment: Payment) => Promise<Payment[]>
    deletePayment: (categoryID: string, paymentID: string) => Promise<Payment[]>
    changePayment: (payment: Payment) => Promise<Payment[]>
}