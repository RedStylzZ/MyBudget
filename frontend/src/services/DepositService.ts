import {Deposit, IDepositController} from "../models/Deposit";
import axios from "axios";
import {ITokenConfig} from "../models/Connection";

export default function DepositService(config: ITokenConfig | undefined): IDepositController {

    return {
        addDeposit: (deposit: Deposit) => {
            return axios.post(`/api/deposit`, deposit, config).then(response => response.data)
        },
        changeDeposit: (deposit: Deposit) => {
            return axios.put(`/api/deposit`, deposit, config).then(response => response.data)
        },
        deleteDeposit: (depositId: string) => {
            return axios.delete(`/api/deposit/${depositId}`, config).then(response => response.data)
        },
        getDeposits: () => {
            return axios.get(`/api/deposit`, config).then(response => response.data)
        },
        getLatestDeposits: () => {
            return axios.get(`/api/deposit/latest`, config).then(response => response.data)
        }
    }
}
