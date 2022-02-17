import {Deposit} from "../../models/Deposit";
import DepositCard from "./DepositCard";

interface DepositsProps {
    deposits: Deposit[]
    deleteDeposit: (depositId: string) => void
}

export default function DepositsGallery({deposits, deleteDeposit}: DepositsProps) {

    return (
        <div className={"deposits"}>
            {
                deposits.map((deposit, index) =>
                    <DepositCard deposit={deposit} deleteDeposit={deleteDeposit} key={index}/>
                )
            }
        </div>
    )
}
