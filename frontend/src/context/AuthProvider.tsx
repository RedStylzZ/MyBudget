import React, {createContext, ReactElement, useEffect, useState} from "react";
import jwt_decode from 'jwt-decode'
import {TOKEN_KEY} from "../models/Constants";

export interface IAuthContext {
    token?: string,
    jwtDecoded?: { sub?: string },
    setJwt: (jwt: string) => void,
    logout: () => void,
}

export const AuthContext = createContext<IAuthContext>({
    logout(): void {throw new Error("Login not initialized")},
    setJwt: () => {throw new Error("Login not initialized")}
})

export default function AuthProvider({children}: { children: ReactElement<any, any> }) {

    const [token, setToken] = useState<string>(localStorage.getItem(TOKEN_KEY) || "")
    const [jwtDecoded, setJwtDecoded] = useState({})

    useEffect(() => {
        if (token !== "") {
            localStorage.setItem(TOKEN_KEY, token)
            setJwtDecoded(jwt_decode(token))
        }
    }, [token])

    const setJwt = (jwt: string) => setToken(jwt)
    const logout = () => setJwt("")

    return (
        <AuthContext.Provider value={{token, jwtDecoded, setJwt, logout}}>
            {children}
        </AuthContext.Provider>
    )
}