import './NavBar.scss'
import {useNavigate} from "react-router-dom";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import RequireAdmin from "../RequireAdmin";
import {DataContext} from "../../context/DataProvider";
import NavButton from "./NavButton";

export default function NavBar() {
    const {logout} = useContext(AuthContext)
    const {currentPage} = useContext(DataContext)
    const navigate = useNavigate()

    return (
        <nav className={"navBar"}>
            <div className={"navBar-buttons"}>
                <NavButton icon={<i className="fa-solid fa-house"/>} value={"Home"} onClick={() => navigate("/")}
                           currentPage={currentPage}/>
                <NavButton icon={<i className="fa-solid fa-book-open"/>} value={"Categories"}
                           onClick={() => navigate("/categories")} currentPage={currentPage}/>
                <NavButton icon={<i className="fa-solid fa-repeat"/>} value={"Series"}
                           onClick={() => navigate("/series")} currentPage={currentPage}/>
                <NavButton icon={<i className="fa-solid fa-wallet"/>} value={"Deposits"}
                           onClick={() => navigate("/deposits")} currentPage={currentPage}/>
            </div>

            <div className={"navBar-buttons"}>
                <RequireAdmin>
                    <NavButton icon={<i className="fa-solid fa-lock"/>} value={"Admin Page"}
                               onClick={() => navigate("/admin")} currentPage={currentPage}/>
                </RequireAdmin>
                <NavButton icon={<i className="fa-solid fa-right-from-bracket"/>} value={"Logout"} onClick={logout}/>
            </div>
        </nav>
    )
}