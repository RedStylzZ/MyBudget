import './NavBar.scss'
import {Link} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {ILoginController} from "../models/ControllerTypes";
import LoginController from "../controllers/LoginController";

export default function NavBar() {
    const {config, logout} = useContext(AuthContext)
    const controller: ILoginController = LoginController()
    const [isAdmin, setIsAdmin] = useState<boolean>(false)

    useEffect(() => {
        controller.isAdmin(config!).then(setIsAdmin)
    }, [controller, config])

    return (
        <div className={"navBar"}>
            <Link to={"/"}>
                <input type={"button"} value={"Home"}/>
            </Link>
            <Link to={"/categories"}>
                <input type={"button"} value={"Categories"}/>
            </Link>
            <input type={"button"} value={"Logout"} onClick={() => logout()}/>
            {
                isAdmin ? <Link to={"/admin"}>
                    <input type={"button"} value={"Admin Page"}/>
                </Link> : null
            }
        </div>
    )
}