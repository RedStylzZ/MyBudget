import {ITokenConfig} from "../models/Connection";
import UserService from "../services/UserService";
import User, {IUserController} from "../models/User";

export default function UserController(config: ITokenConfig | undefined): IUserController {
    const service: IUserController = UserService(config)

    return {
        addUser: (user: User) => {
            return service.addUser(user)
        },
        isAdmin: () => {
            return service.isAdmin()
        }
    }


}