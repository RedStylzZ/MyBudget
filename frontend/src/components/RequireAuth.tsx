import {ReactElement, useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {Navigate} from "react-router-dom";
import {ILoginController} from "../models/ControllerTypes";
import LoginController from "../controllers/LoginController";

export default function RequireAuth({children}: { children: ReactElement<any, any> }) {
    const {token, logout} = useContext(AuthContext)
    const controller: ILoginController = LoginController()
    controller.checkLoggedIn().catch( () => logout());

    return token ? children : <Navigate to="/login"/>
}