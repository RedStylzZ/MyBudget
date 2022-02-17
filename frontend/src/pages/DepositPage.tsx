import {Deposit, IDepositController} from "../models/Deposit";
import React, {ChangeEvent, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import DepositController from "../controllers/DepositController";
import {ITokenConfig} from "../models/Connection";
import {AuthContext} from "../context/AuthProvider";
import DepositsGallery from "../components/deposit/DepositsGallery";
import './DepositPage.scss'
import {DatePicker, LocalizationProvider} from "@mui/lab";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import {TextField} from "@mui/material";
import Button from "../components/Button";

export default function DepositPage() {
    const config: ITokenConfig | undefined = useContext(AuthContext).config
    const controller: IDepositController = useMemo(() => DepositController(config), [config])
    const [deposits, setDeposits] = useState<Deposit[]>([])
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(0)
    const [date, setDate] = useState<Date>(new Date(Date.now()))

    useEffect(() => {
        controller.getDeposits().then(setDeposits)
    }, [controller])

    const addDeposit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (description.length && amount && date) {
            const depositDate: Date = new Date(date.toDateString())
            controller.addDeposit({description, depositDate, amount}).then(setDeposits)
        }
    }

    const deleteDeposit = (depositId: string) => {
        if (depositId && depositId.length) {
            controller.deleteDeposit(depositId).then(setDeposits)
        }
    }

    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => setAmount(parseInt(event.target.value))
    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())

    return (
        <div className={"depositPage"}>
            <div className={"depositHeader"}>
                <h1>Deposit Page</h1>
            </div>
            <div className={"depositsField"}>
                <form onSubmit={addDeposit}>
                    <h2>Description</h2>
                    <input type="text" onChange={onDescriptionChange} value={description}/>
                    <h2>Amount</h2>
                    <input type="number" onChange={onAmountChange} value={amount}/>
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
                <DepositsGallery deposits={deposits} deleteDeposit={deleteDeposit}/>
            </div>
        </div>
    )
}
