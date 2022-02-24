export interface Category {
    categoryId: string;
    categoryName: string;
    paymentSum?: number;
}

export type IDeleteCategory = (categoryId: string) => void
export type IRenameCategory = (category: Category) => void

export interface ICategoryController {
    getCategories: () => Promise<Category[]>
    addCategory: (categoryName: string) => Promise<Category[]>
    deleteCategory: (categoryId: string) => Promise<Category[]>
    renameCategory: (category: Category) => Promise<Category[]>
}