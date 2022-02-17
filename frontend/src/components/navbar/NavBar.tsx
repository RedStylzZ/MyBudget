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
                    <Button value={"Home"}/>
                </Link>
                <Link to={"/categories"}>
                    <Button value={"Categories"}/>
                </Link>
                <Link to={"/series"}>
                    <Button value={"Series"}/>
                </Link>
                <Link to={"/deposits"}>
                    <Button value={"Deposits"}/>
                </Link>
                <Link to={"#"}>
                    <Button value={"Logout"} onClick={logout}/>
                </Link>

                <RequireAdmin>
                    <Link to={"/admin"}>
                        <Button value={"Admin Page"}/>
                    </Link>
                </RequireAdmin>
            </div>
        </nav>
    )
}