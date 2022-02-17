import React, {createContext, ReactElement, useContext, useEffect, useMemo, useState} from "react";
import {AuthContext} from "./AuthProvider";
import UserController from "../controllers/UserController";
import {IUserController} from "../models/User";

export interface IDataContextProps {
    isAdmin?: boolean
}

export const DataContext = createContext<IDataContextProps>({})

export default function DataProvider({children}: { children: ReactElement<any, any> }) {
    const {config, loggedIn, isLoggedIn} = useContext(AuthContext)
    const controller: IUserController = useMemo(() => UserController(config), [config])
    const [isAdmin, setIsAdmin] = useState<boolean | undefined>(undefined)

    useEffect(() => {
        isLoggedIn()
        loggedIn && controller.isAdmin().then(setIsAdmin)
    }, [isLoggedIn, loggedIn, controller])

    if (isAdmin === true && !loggedIn) setIsAdmin(false)

    return (
        <DataContext.Provider value={{isAdmin}}>
            {children}
        </DataContext.Provider>
    )
}