import {Deposit, IDepositController} from "../models/Deposit";
import axios from "axios";
import {ITokenConfig} from "../models/Connection";

export default function DepositService(config: ITokenConfig): IDepositController {

    return {
        addDeposit(deposit: Deposit): Promise<Deposit[]> {
            return axios.post(`/api/deposit`, deposit, config)
        },
        changeDeposit(deposit: Deposit): Promise<Deposit[]> {
            return axios.put(`/api/deposit`, deposit, config)
        },
        deleteDeposit(depositId: string): Promise<Deposit[]> {
            return axios.delete(`/api/deposit/${depositId}`, config)
        },
        getDeposits(): Promise<Deposit[]> {
            return axios.get(`/api/deposit`, config)
        }
    }
}