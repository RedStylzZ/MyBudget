import {ReactElement} from "react";
import {Navigate} from "react-router-dom";
import {ILoginController} from "../models/ControllerTypes";
import LoginController from "../controllers/LoginController";

export default function RequireAuth({children}: { children: ReactElement<any, any> }) {
    const controller: ILoginController = LoginController()

    return controller.checkLoggedIn() ? children : <Navigate to="/login"/>
}