export interface Payment {
    paymentID: string;
    categoryID: string;
    description: string;
    amount: number;
    payDate: Date;
}

export interface PaymentDTO {
    description: string;
    amount: number;
}