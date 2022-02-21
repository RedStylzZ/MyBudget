import React, {ChangeEvent, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import SeriesController from "../controllers/SeriesController";
import {DepositSeries, ISeriesController, PaymentSeries} from "../models/Series";
import {PaymentDTO} from "../models/Payment";
import SeriesItems from "../components/series/SeriesItems";
import {AuthContext} from "../context/AuthProvider";
import './SchedulingPage.scss'
import CategoryController from "../controllers/CategoryController";
import {Category, ICategoryController} from "../models/Category";
import {DateRangePicker, LocalizationProvider} from "@mui/lab";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import {Box, TextField} from "@mui/material";
import {DateRange} from "@mui/lab/DateRangePicker/RangeTypes";
import Button from "../components/Button";
import {DepositDTO} from "../models/Deposit";
import InputBox from "../components/InputBox";

interface SelectInput {
    selectCategory: { value: string }
}

export default function SchedulePage() {
    const config = useContext(AuthContext).config
    const [scheduledDate, setScheduledDate] = useState<number>(1)
    const [description, setDescription] = useState<string>("")
    const [typeName, setTypeName] = useState<string>("Payment")
    const [amount, setAmount] = useState<number>(1)

    const [paymentSeries, setPaymentSeries] = useState<PaymentSeries[]>([])
    const [depositSeries, setDepositSeries] = useState<DepositSeries[]>([])
    const [categories, setCategories] = useState<Category[]>([])
    const [rangeValue, setRangeValue] = useState<DateRange<Date>>([null, null])
    const seriesController: ISeriesController = useMemo(() => SeriesController(config), [config])
    const categoryController: ICategoryController = useMemo(() => CategoryController(config), [config])

    useEffect(() => {
        seriesController.getPaymentSeries().then(setPaymentSeries)
        seriesController.getDepositSeries().then(setDepositSeries)
        categoryController.getCategories().then(setCategories)
    }, [seriesController, categoryController])

    const addSeries = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (typeName === "Payment") {
            const form = event.currentTarget
            const formElements = form.elements as typeof form.elements & SelectInput
            const categoryID: string = formElements.selectCategory.value

            if (!categoryID || !categoryID.length) return

            const payment: PaymentDTO = {description, amount, categoryID}
            const seriesObj: PaymentSeries = {scheduledDate, payment, startDate: rangeValue[0], endDate: rangeValue[1]}
            seriesController.addPaymentSeries(seriesObj).then(setPaymentSeries)
        } else {
            const deposit: DepositDTO = {description, amount}
            const seriesObj: DepositSeries = {scheduledDate, deposit, startDate: rangeValue[0], endDate: rangeValue[1]}
            seriesController.addDepositSeries(seriesObj).then(setDepositSeries)
        }
    }

    const deleteSeries = (seriesId: string | undefined, type: string) => {
        if (seriesId) {
            if (type === "payment") {
                seriesController.deletePaymentSeries(seriesId).then(setPaymentSeries)
            } else if (type === "deposit") {
                seriesController.deleteDepositSeries(seriesId).then(setDepositSeries)
            }
        }
    }

    const onSchedulingDateChange = (event: ChangeEvent<HTMLInputElement>) => setScheduledDate(parseInt(event.target.value))
    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => setAmount(parseInt(event.target.value))
    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())
    const onTypeChange = (event: ChangeEvent<HTMLInputElement>) => {
        setTypeName(event.target.value)
        document.getElementById("selectCategory")!.hidden = event.target.value !== "Payment";
    }

    return (
        <div className={"schedulingPage"}>
            <div className={"schedulingHeader"}>
                <h1>Scheduling Page</h1>
            </div>
            <div className={"series"}>
                <form onSubmit={addSeries} className={"addSeries"}>
                    <h2>Scheduled Day</h2>
                    <InputBox type="number" onChange={onSchedulingDateChange} placeholder={"Scheduling Date"}
                              value={scheduledDate} min={1} max={31}/>
                    <h2>{typeName}</h2>
                    <div className={"roleCheck"}>
                        <input type={"radio"} id={"typePayment"} name={"type"} onChange={onTypeChange} value={"Payment"}
                               defaultChecked={true}/>
                        <label htmlFor="typePayment">Payment</label>
                        <input type={"radio"} id={"typeDeposit"} name={"type"} onChange={onTypeChange}
                               value={"Deposit"}/>
                        <label htmlFor="typeDeposit">Deposit</label>
                    </div>
                    <InputBox type="text" id={"description"} onChange={onDescriptionChange} value={description}
                              placeholder={"Description"}/>
                    <InputBox type="number" id={"amount"} onChange={onAmountChange} placeholder={"Amount"} value={amount}
                              step={0.01}/><br/>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DateRangePicker
                            startText="Check-in"
                            endText="Check-out"
                            value={rangeValue}
                            onChange={(newValue) => {
                                setRangeValue(newValue);
                            }}
                            renderInput={(startProps, endProps) => (
                                <React.Fragment>
                                    <TextField {...startProps} sx={{color: 'white', border: 'white solid 1px'}}/>
                                    <Box sx={{mx: 2}}> to </Box>
                                    <TextField {...endProps} sx={{color: 'white', border: 'white solid 1px'}}/>
                                </React.Fragment>
                            )}
                        />
                    </LocalizationProvider>
                    <select name="Category" id="selectCategory">
                        {
                            categories.map((category, index) =>
                                <option value={category.categoryID} key={index}>{category.categoryName}</option>
                            )
                        }
                    </select>
                    <Button type={"submit"} value={"Add Series"}/>
                </form>
                <SeriesItems paymentSeries={paymentSeries} depositSeries={depositSeries} deleteSeries={deleteSeries}/>
            </div>
        </div>
    )
}
