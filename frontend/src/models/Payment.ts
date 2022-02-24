export interface Payment {
    paymentId: string;
    categoryId: string;
    description: string;
    amount: number;
    payDate: Date;
}

export interface PaymentDTO {
    description: string;
    categoryId: string;
    amount: number;
}

export interface IPaymentController {
    getPayment: (categoryId: string, paymentId: string) => Promise<Payment>
    getPayments: (categoryId: string) => Promise<Payment[]>
    getLastPayments: () => Promise<Payment[]>
    addPayment: (payment: Payment) => Promise<Payment[]>
    deletePayment: (categoryId: string, paymentId: string) => Promise<Payment[]>
    changePayment: (payment: Payment) => Promise<Payment[]>
}