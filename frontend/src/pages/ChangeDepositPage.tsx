import {useNavigate, useParams} from "react-router-dom";
import {ITokenConfig} from "../models/Connection";
import React, {ChangeEvent, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {IDepositController} from "../models/Deposit";
import DepositController from "../controllers/DepositController";
import {DatePicker, LocalizationProvider} from "@mui/lab";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import {TextField} from "@mui/material";
import './ChangeDepositPage.scss'
import InputBox from "../components/InputBox";
import Button from "../components/Button";

export default function ChangeDepositPage() {
    const params = useParams()
    const navigate = useNavigate()
    const depositId = params.depositId
    const config: ITokenConfig | undefined = useContext(AuthContext).config
    const controller: IDepositController = useMemo(() => DepositController(config), [config])
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(0)
    const [date, setDate] = useState<Date>(new Date(Date.now()))

    useEffect(() => {
        if (depositId === undefined) {
            navigate("/")
        } else {
            controller.getDeposit(depositId!).then(deposit => {
                setDescription(deposit.description)
                setAmount(deposit.amount)
                setDate(deposit.depositDate)
            })
        }
    }, [controller, depositId, navigate])

    const changeDeposit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (description.length && amount && date && depositId !== undefined) {
            const depositDate: Date = new Date(date.toDateString())
            controller.changeDeposit({depositId, description, depositDate, amount}).then(() => navigate("/deposits")
            )
        }
    }

    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => setAmount(parseInt(event.target.value))
    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())

    return (
        <div className={"changeDepositPage"}>
            <div className={"depositHeader"}>
                <h1>Deposit Change Page</h1>
            </div>
            <form onSubmit={changeDeposit}>
                <div>
                    <h2>Description</h2>
                    <InputBox type="text" onChange={onDescriptionChange} value={description}/>
                </div>
                <div>
                    <h2>Amount</h2>
                    <InputBox type="number" onChange={onAmountChange} value={amount}/>
                </div>
                <div className={"depositDate"}>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                            label="Pick your date"
                            value={date}
                            inputFormat={"dd/MM/yyyy"}
                            onChange={(newValue) => {
                                if (newValue) {
                                    setDate(newValue);
                                }
                            }}
                            renderInput={(params) => <TextField {...params} sx={{
                                color: 'white',
                                backgroundColor: '#1A1A1A',
                                '&:hover': {
                                    backgroundColor: '#1B1B1B',
                                    opacity: [0.9, 0.8, 0.7],
                                },
                            }} id={"depositDate"}/>}
                        />
                    </LocalizationProvider>
                </div>
                <Button submit={true} value={"Add Deposit"}/>
            </form>
        </div>
    )
}