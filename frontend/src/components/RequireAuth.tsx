import {ReactElement} from "react";
import {Navigate} from "react-router-dom";
import LoginController from "../controllers/LoginController";
import {ILoginController} from "../models/Login";

export default function RequireAuth({children}: { children: ReactElement<any, any> }) {
    const controller: ILoginController = LoginController()

    return controller.checkLoggedIn() ? children : <Navigate to="/login"/>
}