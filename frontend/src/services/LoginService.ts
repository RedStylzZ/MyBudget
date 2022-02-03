import axios from "axios";
import {ILoginController} from "../models/ControllerTypes";

export default function LoginService(): ILoginController {
    return {
        login: (username: string, password: string) => {
            return axios.post("/auth/login",
                {username: username, password: password})
                .then(response => response.data);
        }
    }
}