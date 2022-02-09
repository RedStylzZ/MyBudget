import {ReactElement, useContext} from "react";
import {DataContext} from "../context/DataProvider";

export default function RequireAdmin({children}: { children: ReactElement<any, any> }) {
    const {isAdmin} = useContext(DataContext)

    console.log("IsAdmin:", isAdmin)

    return isAdmin === true ? children : null
}
