export interface Category {
    categoryID: string;
    categoryName: string;
    paymentSum?: number;
}

export type IDeleteCategory = (categoryID: string) => void
export type IRenameCategory = (category: Category) => void