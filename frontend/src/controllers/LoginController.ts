import LoginService from "../services/LoginService";
import {ILoginController, ILoginService} from "../models/ControllerTypes";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {ITokenConfig} from "../models/Connection";

export default function LoginController(): ILoginController {

    const service: ILoginService = LoginService();
    const {token, jwtDecoded} = useContext(AuthContext)

    const isValidToken = (): boolean => !!jwtDecoded && (jwtDecoded.exp * 1000) > Date.now()

    return {
        login: (username: string, password: string) => {
            return service.login(username, password);
        },
        checkLoggedIn: () => {
            return !!token && isValidToken()
        },
        isAdmin: (config: ITokenConfig) => {
            return service.isAdmin(config)
        }
    }
}
