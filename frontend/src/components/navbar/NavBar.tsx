import './NavBar.scss'
import {Link} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import RequireAdmin from "../RequireAdmin";

export default function NavBar() {
    const {logout} = useContext(AuthContext)

    return (
        <nav className={"navBar"}>
            <div>
                <Link to={"/"}>
                    <span>Home</span>
                </Link>
                <Link to={"/categories"}>
                    <span>Categories</span>
                </Link>
                <Link to={"/series"}>
                    <span>Series</span>
                </Link>
                <Link to={"/deposits"}>
                    <span>Deposits</span>
                </Link>
                <span onClick={() => logout()}>Logout</span>

                <RequireAdmin>
                    <Link to={"/admin"}>
                        <span>AdminPage</span>
                    </Link>
                </RequireAdmin>
            </div>
        </nav>
    )
}