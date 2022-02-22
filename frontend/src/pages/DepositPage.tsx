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
import InputBox from "../components/InputBox";
import {DataContext} from "../context/DataProvider";

export default function DepositPage() {
    const config: ITokenConfig | undefined = useContext(AuthContext).config
    const {setCurrentPage} = useContext(DataContext)
    const controller: IDepositController = useMemo(() => DepositController(config), [config])
    const [deposits, setDeposits] = useState<Deposit[]>([])
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(0)
    const [date, setDate] = useState<Date>(new Date(Date.now()))

    useEffect(() => {
        controller.getDeposits().then(setDeposits)
    }, [controller])

    useEffect(() => {
        setCurrentPage("Deposits")
    }, [setCurrentPage])

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
            <div className={"depositsField"}>
                <form onSubmit={addDeposit} className={"addDeposit"}>
                    <h2>Description</h2>
                    <InputBox type="text" onChange={onDescriptionChange} value={description} placeholder={"Description"}/>
                    <h2>Amount</h2>
                    <InputBox type="number" onChange={onAmountChange} value={amount} placeholder={"Amount"}/>
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
                                    border: '1px solid white',
                                    '&:hover': {
                                        backgroundColor: '#1B1B1B',
                                        opacity: [0.9, 0.8, 0.7],
                                    },
                                }} id={"depositDate"}/>}
                            />
                        </LocalizationProvider>
                    </div>
                    <Button type={"submit"} value={"Add Deposit"}/>
                </form>
                <DepositsGallery deposits={deposits} deleteDeposit={deleteDeposit}/>
            </div>
        </div>
    )
}
