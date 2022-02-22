import './NavBar.scss'
import {useNavigate} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import RequireAdmin from "../RequireAdmin";
import Button from "../Button";
import {DataContext} from "../../context/DataProvider";

export default function NavBar() {
    const {logout} = useContext(AuthContext)
    const {currentPage} = useContext(DataContext)
    const navigate = useNavigate()

    return (
        <nav className={"navBar"}>
            <div>
                <Button value={"Home"} onClick={() => navigate("/")} currentPage={currentPage}/>
                <Button value={"Categories"} onClick={() => navigate("/categories")} currentPage={currentPage}/>
                <Button value={"Series"} onClick={() => navigate("/series")} currentPage={currentPage}/>
                <Button value={"Deposits"} onClick={() => navigate("/deposits")} currentPage={currentPage}/>
            </div>

            <div>
                <RequireAdmin>
                    <Button value={"Admin Page"} onClick={() => navigate("/admin")} currentPage={currentPage}/>
                </RequireAdmin>
                <Button value={"Logout"} onClick={logout}/>
            </div>
        </nav>
    )
}