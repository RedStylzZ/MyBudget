import DepositService from "../services/DepositService";
import {Deposit, IDepositController} from "../models/Deposit";
import {ITokenConfig} from "../models/Connection";

export default function DepositController(config: ITokenConfig): IDepositController {

    const service: IDepositController = DepositService(config)

    return {
        addDeposit(deposit: Deposit): Promise<Deposit[]> {
            return service.addDeposit(deposit)
        },
        changeDeposit(deposit: Deposit): Promise<Deposit[]> {
            return service.changeDeposit(deposit)
        },
        deleteDeposit(depositId: string): Promise<Deposit[]> {
            return service.deleteDeposit(depositId)
        },
        getDeposits(): Promise<Deposit[]> {
            return service.getDeposits()
        }

    }
}