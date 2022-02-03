import {ReactElement, useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {Navigate} from "react-router-dom";

export default function RequireAuth({children}: { children: ReactElement<any, any> }) {
    const {token} = useContext(AuthContext)

    return token ? children : <Navigate to="/login"/>
}