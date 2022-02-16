import {Deposit} from "../../models/Deposit";
import FormatDate from "../FormatDate";
import MonetaryValue from "../MonetaryValue";
import {useNavigate} from "react-router-dom";

interface DepositProps {
    deposit: Deposit
    deleteDeposit: (depositId: string) => void
}

export default function DepositCard({deposit, deleteDeposit}: DepositProps) {
    const navigate = useNavigate()

    return (
        <div className={"depositCard"} id={deposit.depositId}>
            <h1>{deposit.description}</h1>
            <h2>{<MonetaryValue amount={deposit.amount}/>}</h2>
            <h2>{<FormatDate date={deposit.depositDate}/>}</h2>
            <div>
                <input type="button" onClick={() => deleteDeposit(deposit.depositId!)} value={"Delete"}/>
                <input type="button" onClick={() => navigate(`/deposits/change/${deposit.depositId}`)}
                       value={"Change"}/>
            </div>
        </div>
    )
}
