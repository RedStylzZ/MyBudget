import {IPaymentController, Payment} from "../../models/Payment";
import PaymentItem from "./PaymentItem";
import React, {FormEvent, useState} from "react";
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import {DatePicker, LocalizationProvider} from "@mui/lab";
import {Alert, TextField} from "@mui/material";
import Button from "../Button";
import InputBox from "../InputBox";
import {errors} from "../../models/Constants";

interface PaymentsProps {
    payments: Payment[]
    categoryId: string
    setPayments: React.Dispatch<React.SetStateAction<Payment[]>>
    controller: IPaymentController
}

interface IPaymentInput {
    description: { value: string }
    amount: { value: number }
    payDate: { value: string }
}

export default function Payments({payments, categoryId, setPayments, controller}: PaymentsProps) {
    const [date, setDate] = useState<Date>(new Date(Date.now()))
    const [descriptionError, setDescriptionError] = useState<boolean>(false)
    const [amountError, setAmountError] = useState<boolean>(false)

    if (!Array.isArray(payments)) return null;


    const addPayment = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const form = event.currentTarget
        const formElements = form.elements as typeof form.elements & IPaymentInput
        const paymentId: string = "";
        const description: string = formElements.description.value
        const amount: number = formElements.amount.value

        if (!description) {
            setDescriptionError(true)
            return
        }
        if (!amount) {
            setAmountError(true)
            return
        }

        const payDate: Date = new Date(date.toDateString())
        const payment: Payment = {
            paymentId: paymentId, categoryId: categoryId, description, amount, payDate
        }
        controller.addPayment(payment).then(setPayments)
    }

    const deletePayment = (_categoryId: string) => (paymentId: string) => {
        controller.deletePayment(_categoryId, paymentId).then(setPayments)
    }

    return (
        <div className={"payments"}>
            <h1>Payments</h1>
            <form onSubmit={addPayment} className={"addPaymentForm"}>
                {descriptionError ? <Alert severity={"error"} onClick={() => setDescriptionError(false)}>{errors.description}</Alert> : null}
                {amountError ? <Alert severity={"error"} onClick={() => setAmountError(false)}>{errors.amount}</Alert> : null}
                <InputBox id={"description"} placeholder={"Description"}/>
                <InputBox type={"number"} id={"amount"} placeholder={"Amount"} step={0.01}/>
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
                <Button value={"Add Payment"} type={"submit"}/>
            </form>
            {
                payments.map((payment, index) =>
                    <PaymentItem payment={payment} key={index} deletePayment={deletePayment(categoryId)}
                                 categoryId={categoryId}/>
                )
            }
        </div>
    )
}