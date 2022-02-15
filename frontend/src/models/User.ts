export default interface User {
    username: string
    password: string
    rights: [string]
}

export interface IUserController {
    isAdmin: () => Promise<boolean>
    addUser: (user: User) => Promise<boolean>
}