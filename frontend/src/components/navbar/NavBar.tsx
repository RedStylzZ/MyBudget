import './NavBar.scss'
import {useNavigate} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import RequireAdmin from "../RequireAdmin";
import Button from "../Button";

export default function NavBar() {
    const {logout} = useContext(AuthContext)
    const navigate = useNavigate()

    return (
        <nav className={"navBar"}>
            <div>
                <Button value={"Home"} onClick={() => navigate("/")}/>
                <Button value={"Categories"} onClick={() => navigate("/categories")}/>
                <Button value={"Series"} onClick={() => navigate("/series")}/>
                <Button value={"Deposits"} onClick={() => navigate("/deposits")}/>
            </div>

            <div>
                <RequireAdmin>
                    <Button value={"Admin Page"} onClick={() => navigate("/admin")}/>
                </RequireAdmin>
                <Button value={"Logout"} onClick={logout}/>
            </div>
        </nav>
    )
}