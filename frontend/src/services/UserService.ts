import {ITokenConfig} from "../models/Connection";
import {IUserController} from "../models/ControllerTypes";
import axios from "axios";

export default function UserService(config: ITokenConfig | undefined): IUserController {

    return {
        addUser: (user) => {
            return axios.post("/api/user/", user, config).then(response => response.data)
        },
        isAdmin: () => {
            return axios.get("/api/user/admin", config).then(response => {
                console.log(response.data)
                return response.data
            })
        }
    }
}
