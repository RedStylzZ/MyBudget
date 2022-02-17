import DepositService from "../services/DepositService";
import {Deposit, IDepositController} from "../models/Deposit";
import {ITokenConfig} from "../models/Connection";

export default function DepositController(config: ITokenConfig | undefined): IDepositController {

    const service: IDepositController = DepositService(config)

    return {
        addDeposit: (deposit: Deposit) => {
            return service.addDeposit(deposit)
        },
        changeDeposit: (deposit: Deposit) => {
            return service.changeDeposit(deposit)
        },
        deleteDeposit: depositId => {
            return service.deleteDeposit(depositId)
        },
        getDeposits: () => {
            return service.getDeposits()
        },
        getLatestDeposits: () => {
            return service.getLatestDeposits()
        },
        getDeposit: (depositId: string) => {
            return service.getDeposit(depositId)
        }
    }
}
