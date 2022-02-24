import {Navigate, useNavigate} from "react-router-dom";
import {ChangeEvent, FormEvent, useContext, useState} from "react";
import LoginController from "../controllers/LoginController";
import {AuthContext} from "../context/AuthProvider";
import './LoginPage.scss'
import {ILoginController} from "../models/Login";
import Button from "../components/Button";
import InputBox from "../components/InputBox";
import {Alert} from "@mui/material";

export default function LoginPage() {
    const {setJwt} = useContext(AuthContext)
    const controller: ILoginController = LoginController()
    const navigate = useNavigate()
    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [loginError, setLoginError] = useState<boolean>(false);

    const login = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const formUsername: string = name.trim()
        const formPassword: string = password.trim()

        if ((formUsername && formUsername.length > 0) && (formPassword && formPassword.length > 0)) {
            controller.login(formUsername, formPassword)
                .then(newToken => {
                    setJwt(newToken)
                    navigate("/")
                })
                .catch(() => setLoginError(true))
        }
    }

    const onNameChange = (event: ChangeEvent<HTMLInputElement>) => setName(event.target.value)
    const onPasswordChange = (event: ChangeEvent<HTMLInputElement>) => setPassword(event.target.value)

    if (controller.checkLoggedIn()) {
        return <Navigate to="/"/>
    }

    return (
        <div className={"loginPage"}>
            <h1>{"Login"}</h1>
            <form onSubmit={login}>
                {loginError ?
                    <Alert severity={"error"} onClick={() => setLoginError(false)}>Invalid credentials</Alert> : null}
                <h2>Username</h2>
                <InputBox type="text" id={"username"} onChange={onNameChange} value={name}
                          placeholder={"Username"}/>
                <h2>Password</h2>
                <InputBox type="password" id={"password"} onChange={onPasswordChange} value={password}
                          placeholder={"Password"}/>
                <Button type={"submit"} value={"Submit"}/>
            </form>
        </div>
    )
}
