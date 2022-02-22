import React, {createContext, ReactElement, useContext, useEffect, useMemo, useState} from "react";
import {AuthContext} from "./AuthProvider";
import UserController from "../controllers/UserController";
import {IUserController} from "../models/User";

export type PageName = "None" | "Home" | "Categories" | "Series" | "Deposits" | "Admin Page"

export interface IDataContextProps {
    isAdmin?: boolean
    currentPage: PageName
    setCurrentPage: (currentPage: PageName) => void
}

export const DataContext = createContext<IDataContextProps>({
    currentPage: "None",
    setCurrentPage: () => {}
})

export default function DataProvider({children}: { children: ReactElement<any, any> }) {
    const {config, loggedIn, isLoggedIn} = useContext(AuthContext)
    const controller: IUserController = useMemo(() => UserController(config), [config])
    const [isAdmin, setIsAdmin] = useState<boolean | undefined>(undefined)
    const [currentPage, setCurrentPage] = useState<PageName>("None");

    useEffect(() => {
        isLoggedIn()
        loggedIn && controller.isAdmin().then(setIsAdmin)
    }, [isLoggedIn, loggedIn, controller])

    if (isAdmin === true && !loggedIn) setIsAdmin(false)

    return (
        <DataContext.Provider value={{isAdmin, currentPage, setCurrentPage}}>
            {children}
        </DataContext.Provider>
    )
}