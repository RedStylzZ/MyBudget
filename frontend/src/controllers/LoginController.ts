import LoginService from "../services/LoginService";
import {ILoginController} from "../models/ControllerTypes";

export default function LoginController(): ILoginController {

    const service = LoginService();

    return {
        login: (username: string, password: string) => {
            return service.login(username, password);
        },
        checkLoggedIn: () => {
            return service.checkLoggedIn().then(response => response)
        }
    }
}