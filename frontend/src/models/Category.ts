export interface Category {
    categoryID:   string;
    userID:       string;
    categoryName: string;
    paymentSum:   number;
}

export type  IDeleteCategory = (categoryID: string) => void