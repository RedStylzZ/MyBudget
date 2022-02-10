import React, {createContext, ReactElement, useEffect, useState} from "react";
import jwt_decode from 'jwt-decode'
import {TOKEN_KEY} from "../models/Constants";
import {ITokenConfig} from "../models/Connection";
import {isValidToken} from "../controllers/LoginController";

export interface IAuthContext {
    token?: string,
    config?: ITokenConfig,
    jwtDecoded?: { sub: string, exp: number },
    setJwt: (jwt: string) => void,
    logout: () => void,
    loggedIn: boolean,
    isLoggedIn: () => void
}

export const AuthContext = createContext<IAuthContext>({
    logout: () => {
        throw new Error("Login not initialized")
    },
    setJwt: () => {
        throw new Error("Login not initialized")
    },
    loggedIn: false,
    isLoggedIn: () => false
})

export default function AuthProvider({children}: { children: ReactElement<any, any> }) {

    const [token, setToken] = useState<string | undefined>(localStorage.getItem(TOKEN_KEY) || undefined)
    const [config, setConfig] = useState<ITokenConfig>({headers: {Authorization: `Bearer ${token}`}});
    const [jwtDecoded, setJwtDecoded] = useState(token ? jwt_decode(token) : undefined)
    const [loggedIn, setLoggedIn] = useState<boolean>(false)

    useEffect(() => {
        if (token !== undefined) {
            localStorage.setItem(TOKEN_KEY, token)
            if (token) {
                setJwtDecoded(jwt_decode(token))
                setConfig({headers: {Authorization: `Bearer ${token}`}})
            }
        }
    }, [token])

    const setJwt = (jwt: string) => setToken(jwt)
    const logout = () => {
        setJwt("")
    }

    const isLoggedIn = () => setLoggedIn(!!token && isValidToken(jwtDecoded))

    return (
        <AuthContext.Provider value={{token, config, jwtDecoded, setJwt, logout, isLoggedIn, loggedIn}}>
            {children}
        </AuthContext.Provider>
    )
}