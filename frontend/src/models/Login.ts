export interface ILoginController {
    login: (username: string, password: string) => Promise<string>
    checkLoggedIn: () => boolean
}

export interface ILoginService {
    login: (username: string, password: string) => Promise<string>
}