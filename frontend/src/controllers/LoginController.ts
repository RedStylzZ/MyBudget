import LoginService from "../services/LoginService";
import {ILoginController} from "../models/ControllerTypes";

export default function LoginController(): ILoginController {

    const apiController = LoginService();

    return {
        login: (username: string, password: string) => {
            return apiController.login(username, password).then(token => token);
        }
    }
}