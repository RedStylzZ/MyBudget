import LoginService from "../services/LoginService";
import {ILoginController, ILoginService} from "../models/ControllerTypes";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";

export default function LoginController(): ILoginController {

    const service: ILoginService = LoginService();
    const jwtDecoded = useContext(AuthContext).jwtDecoded!

    return {
        login: (username: string, password: string) => {
            return service.login(username, password);
        },
        checkLoggedIn: () => {
            const dateInMilliseconds: number = jwtDecoded.exp! * 1000
            if (isNaN(dateInMilliseconds)) return true
            console.log(jwtDecoded)
            console.log("Token time: " + dateInMilliseconds)
            console.log("Current time: " + Date.now())
            return dateInMilliseconds > Date.now()
        }
    }
}
