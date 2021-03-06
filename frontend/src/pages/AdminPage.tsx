import {ChangeEvent, FormEventHandler, useContext, useEffect, useState} from "react";
import UserController from "../controllers/UserController";
import {AuthContext} from "../context/AuthProvider";
import User, {IUserController} from "../models/User";
import './AdminPage.scss'
import Button from "../components/Button";
import InputBox from "../components/InputBox";
import {DataContext} from "../context/DataProvider";
import {Alert} from "@mui/material";

export default function AdminPage() {
    const {config} = useContext(AuthContext)
    const {setCurrentPage} = useContext(DataContext)
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [worked, setWorked] = useState<boolean | undefined>(undefined)
    const [alertComp, setAlertComp] = useState<JSX.Element>(<></>)
    const [role, setRole] = useState<string>("USER")
    const controller: IUserController = UserController(config)

    useEffect(() => {
        setCurrentPage("Admin Page")
    }, [setCurrentPage])

    useEffect(() => {
        if (worked === true) {
            setAlertComp(<Alert severity={"success"}>Successfully added User</Alert>)
        } else if (worked === false) {
            setAlertComp(<Alert severity={"error"}>Could not add User</Alert>)
        }
    }, [worked])

    const addUser: FormEventHandler<HTMLFormElement> = (event) => {
        event.preventDefault()

        if ((username && username.length) && (password && password.length) && (role && role.length)) {
            const user: User = {
                username: username,
                password: password,
                rights: [role]
            }
            controller.addUser(user).then(response => {
                setWorked(response)
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
                {alertComp}
            </div>
            <div className={"addUserForm"}>
                <form onSubmit={addUser} className={"addUser"}>
                    <h3>Username</h3>
                    <InputBox type="text" id="username" onChange={onUsernameChange} placeholder={"Username"}
                              value={username}/>
                    <h3>Password</h3>
                    <InputBox type="password" id="password" onChange={onPasswordChange} placeholder={"Password"}
                              value={password}/>
                    <h3>Role</h3>
                    <div className={"roleCheck"}>
                        <input type={"radio"} id={"roleUSER"} name={"role"} onChange={onRoleChange} value={"USER"}
                               defaultChecked={true}/>
                        <label htmlFor="roleUSER">USER</label>
                        <input type={"radio"} id={"roleADMIN"} name={"role"} onChange={onRoleChange} value={"ADMIN"}/>
                        <label htmlFor="roleADMIN">ADMIN</label>
                    </div>
                    <Button type={"submit"} value={"Add User"}/>
                </form>
            </div>
        </div>
    )
}