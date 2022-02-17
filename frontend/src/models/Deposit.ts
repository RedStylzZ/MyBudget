export interface IDepositController {
    getDeposits: () => Promise<Deposit[]>
    getDeposit: (depositId: string) => Promise<Deposit>
    getLatestDeposits: () => Promise<Deposit[]>
    addDeposit: (deposit: Deposit) => Promise<Deposit[]>
    changeDeposit: (deposit: Deposit) => Promise<Deposit[]>
    deleteDeposit: (depositId: string) => Promise<Deposit[]>
}

export interface Deposit {
    depositId?: string
    description: string
    amount: number
    depositDate: Date
}
