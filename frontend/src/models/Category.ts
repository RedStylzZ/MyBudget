export interface Category {
    categoryID: string;
    categoryName: string;
    paymentSum?: number;
}

export type IDeleteCategory = (categoryID: string) => void
export type IRenameCategory = (category: Category) => void

export interface ICategoryController {
    getCategories: () => Promise<Category[]>
    addCategory: (categoryName: string) => Promise<Category[]>
    deleteCategory: (categoryID: string) => Promise<Category[]>
    renameCategory: (category: Category) => Promise<Category[]>
}