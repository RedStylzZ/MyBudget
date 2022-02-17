import axios from "axios";
import {ILoginService} from "../models/Login";

export default function LoginService(): ILoginService {
    return {
        login: (username: string, password: string) => {
            return axios.post("/auth/login",
                {username: username, password: password})
                .then(response => response.data);
        }
    }
}