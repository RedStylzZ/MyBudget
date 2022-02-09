import './NavBar.scss'
import {Link} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";

export default function NavBar() {
    const {logout} = useContext(AuthContext)
    return (
        <div className={"navBar"}>
            <Link to={"/"}>
                <input type={"button"} value={"Home"}/>
            </Link>
            <Link to={"/categories"}>
                <input type={"button"} value={"Categories"}/>
            </Link>
            <input type={"button"} value={"Logout"} onClick={() => logout()}/>
        </div>
    )
}