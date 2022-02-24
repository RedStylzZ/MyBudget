import {Navigate, useNavigate, useParams} from "react-router-dom";
import React, {ChangeEvent, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import {DatePicker, LocalizationProvider} from "@mui/lab";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import {TextField} from "@mui/material";
import PaymentController from "../controllers/PaymentController";
import {AuthContext} from "../context/AuthProvider";
import {IPaymentController, Payment} from "../models/Payment";
import './ChangePaymentPage.scss'
import InputBox from "../components/InputBox";
import Button from "../components/Button";

export default function ChangePaymentPage() {
    const urlParams = useParams()
    const categoryId = urlParams.categoryId
    const paymentId = urlParams.paymentId
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(0)
    const [date, setDate] = useState<Date>(new Date(Date.now()))
    const config = useContext(AuthContext).config
    const controller: IPaymentController = useMemo(() => PaymentController(config), [config])
    const navigate = useNavigate()

    useEffect(() => {
        controller.getPayment(categoryId!, paymentId!).then((response) => {
            setDescription(response.description)
            setAmount(response.amount)
            setDate(response.payDate)
        })
    }, [controller, categoryId, paymentId])

    if (!categoryId || !paymentId) return <Navigate to="/categories"/>

    const changePayment = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if ((amount) && (description && description.length) && (date)) {
            const payment: Payment = {
                paymentId: paymentId,
                categoryId: categoryId,
                description,
                amount,
                payDate: new Date(date)
            }
            controller.changePayment(payment).then(() => {
                navigate("/categories")
            })
        }
    }

    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())
    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => setAmount(parseFloat(event.target.value))

    return (
        <div className={"changePaymentPage"}>
            <h1>Change Payment</h1>
            <form onSubmit={changePayment}>
                <h2>Description</h2>
                <InputBox type="text" id={"description"} onChange={onDescriptionChange} value={description}/>
                <h2>Amount</h2>
                <InputBox type={"number"} id={"amount"} onChange={onAmountChange} value={amount} step={0.01}/>
                <h2>PayDate</h2>
                <div className={"payDate"}>
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
                            }} id={"payDate"}/>}
                        />
                    </LocalizationProvider>
                </div>
                <Button type="submit" value={"Change"}/>
            </form>
        </div>
    )
}