import './NavBar.scss'
import {Link} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import RequireAdmin from "../RequireAdmin";

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
            <Link to={"/series"}>
                <input type="button" value={"Series"}/>
            </Link>
            <input type={"button"} value={"Logout"} onClick={() => logout()}/>

            <RequireAdmin>
                <Link to={"/admin"}>
                    <input type={"button"} value={"Admin Page"}/>
                </Link>
            </RequireAdmin>


        </div>
    )
}