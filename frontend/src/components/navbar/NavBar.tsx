import './NavBar.scss'
import {Link} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import RequireAdmin from "../RequireAdmin";
import Button from "../Button";

export default function NavBar() {
    const {logout} = useContext(AuthContext)

    return (
        <nav className={"navBar"}>
            <div>
                <Link to={"/"}>
                    <Button description={"Home"}/>
                </Link>
                <Link to={"/categories"}>
                    <Button description={"Categories"}/>
                </Link>
                <Link to={"/series"}>
                    <Button description={"Series"}/>
                </Link>
                <Link to={"/deposits"}>
                    <Button description={"Deposits"}/>
                </Link>
                <Link to={"#"}>
                    <Button description={"Logout"} onClick={logout}/>
                </Link>

                <RequireAdmin>
                    <Link to={"/admin"}>
                        <Button description={"Admin Page"}/>
                    </Link>
                </RequireAdmin>
            </div>
        </nav>
    )
}