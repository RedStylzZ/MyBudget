import {Navigate, useNavigate} from "react-router-dom";
import {ChangeEvent, FormEvent, useContext, useState} from "react";
import LoginController from "../controllers/LoginController";
import {ILoginController} from "../models/ControllerTypes";
import {AuthContext} from "../context/AuthProvider";
import './LoginPage.scss'

export default function LoginPage() {
    const {token, setJwt} = useContext(AuthContext)
    const controller: ILoginController = LoginController()
    const navigate = useNavigate()
    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")

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
                .catch(console.error)
        }
    }

    const onNameChange = (event: ChangeEvent<HTMLInputElement>) => setName(event.target.value)
    const onPasswordChange = (event: ChangeEvent<HTMLInputElement>) => setPassword(event.target.value)

    if (token) {
        return <Navigate to="/"/>
    }

    return (
        <div className={"loginPage"}>
            <h1>{"Login"}</h1>
            <form onSubmit={login}>
                <h2>Username</h2>
                <input type="text" id={"username"} onChange={onNameChange} value={name}/><br/>
                <input type="password" id={"password"} onChange={onPasswordChange} value={password}/><br/>
                <input type="submit" value={"Submit"}/>
            </form>
        </div>
    )
}