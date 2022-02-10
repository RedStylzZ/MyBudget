import {ChangeEvent, FormEventHandler, useContext, useState} from "react";
import {IUserController} from "../models/ControllerTypes";
import UserController from "../controllers/UserController";
import {AuthContext} from "../context/AuthProvider";
import User from "../models/User";
import './AdminPage.scss'

export default function AdminPage() {
    const {config} = useContext(AuthContext)
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [worked, setWorked] = useState<string>("")
    const [role, setRole] = useState<string>("USER")
    const controller: IUserController = UserController(config)

    const addUser: FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault()

        if ((username && username.length) && (password && password.length) && (role && role.length)) {
            const user: User = {
                username: username,
                password: password,
                rights: [role]
            }
            controller.addUser(user).then(response => {
                const status: string = response ? "Successfully added user" : "Failed to add user"
                setWorked(status)
            })
        }
    }

    const onUsernameChange = (event: ChangeEvent<HTMLInputElement>) =>
        setUsername(event.target.value)

    const onPasswordChange = (event: ChangeEvent<HTMLInputElement>) =>
        setPassword(event.target.value)

    const onRoleChange = (event: ChangeEvent<HTMLInputElement>) =>
        setRole(event.target.value)

    return (
        <div className={"adminPage"}>
            <div className={"adminPageHeader"}>
                <h1>Admin Page</h1>
                <h2>{worked}</h2>
            </div>
            <div className={"addUserForm"}>
                <form onSubmit={addUser} className={"addUser"}>
                    <input type="text" id="username" onChange={onUsernameChange} placeholder={"Username"}
                           value={username}/>
                    <input type="password" id="password" onChange={onPasswordChange} placeholder={"Password"}
                           value={password}/>
                    <input type={"radio"} id={"roleUSER"} name={"role"} onChange={onRoleChange} value={"USER"}
                           defaultChecked={true}/>
                    <label htmlFor="roleUSER">USER</label>
                    <input type={"radio"} id={"roleADMIN"} name={"role"} onChange={onRoleChange} value={"ADMIN"}/>
                    <label htmlFor="roleADMIN">ADMIN</label>
                    <input type="submit" value={"Add User"}/>
                </form>
            </div>
        </div>
    )
}