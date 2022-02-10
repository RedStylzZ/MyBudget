import React, {createContext, ReactElement, useContext, useEffect, useMemo, useState} from "react";
import {AuthContext} from "./AuthProvider";
import {IUserController} from "../models/ControllerTypes";
import UserController from "../controllers/UserController";

export interface IDataContextProps {
    isAdmin?: boolean
}

export const DataContext = createContext<IDataContextProps>({})

export default function DataProvider({children}: { children: ReactElement<any, any> }) {
    const {config, isLoggedIn} = useContext(AuthContext)
    const controller: IUserController = useMemo(() => UserController(config), [config])
    const [isAdmin, setIsAdmin] = useState<boolean | undefined>(undefined)

    useEffect(() => {
        controller.isAdmin().then(setIsAdmin)
    }, [controller])

    if (isAdmin === true && !isLoggedIn()) setIsAdmin(false)

    return (
        <DataContext.Provider value={{isAdmin}}>
            {children}
        </DataContext.Provider>
    )
}