import {ITokenConfig} from "../models/Connection";
import axios from "axios";
import {IUserController} from "../models/User";

export default function UserService(config: ITokenConfig | undefined): IUserController {

    return {
        addUser: (user) => {
            return axios.post("/api/user/", user, config).then(response => response.data)
        },
        isAdmin: () => {
            return axios.get("/api/user/admin", config).then(response => response.data)
        }
    }
}
