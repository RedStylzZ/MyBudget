import './NavBar.scss'
import {Link} from "react-router-dom";

export default function NavBar() {
    return (
        <div className={"navBar"}>
            <Link to={"/"}>
                <input type={"button"} value={"Home"}/>
            </Link>
            <Link to={"/categories"}>
                <input type={"button"} value={"Categories"}/>
            </Link>
            <Link to={"/login"}>
                <input type={"button"} value={"Logout"}/>
            </Link>
        </div>
    )
}