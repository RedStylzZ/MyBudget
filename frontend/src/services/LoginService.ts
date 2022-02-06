import axios from "axios";
import {ILoginController} from "../models/ControllerTypes";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";

export default function LoginService(): ILoginController {
    const config = useContext(AuthContext).config
    return {
        login: (username: string, password: string) => {
            return axios.post("/auth/login",
                {username: username, password: password})
                .then(response => response.data);
        },
        checkLoggedIn: () => {
            return axios.get("/auth/login", config).then(response => response.data)
        }
    }
}