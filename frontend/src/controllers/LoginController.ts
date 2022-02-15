import LoginService from "../services/LoginService";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {ILoginController, ILoginService} from "../models/Login";

export const isValidToken = (jwtDecoded?: { exp: number }): boolean =>
    !!jwtDecoded && (jwtDecoded.exp * 1000) > Date.now()

export default function LoginController(): ILoginController {

    const service: ILoginService = LoginService();
    const {token, jwtDecoded} = useContext(AuthContext)

    return {
        login: (username: string, password: string) => {
            return service.login(username, password);
        },
        checkLoggedIn: () => {
            return !!token && isValidToken(jwtDecoded)
        }
    }
}
