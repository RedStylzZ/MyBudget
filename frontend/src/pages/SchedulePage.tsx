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
import {Alert, Box, TextField} from "@mui/material";
import {DateRange} from "@mui/lab/DateRangePicker/RangeTypes";
import Button from "../components/Button";
import {DepositDTO} from "../models/Deposit";
import InputBox from "../components/InputBox";
import {DataContext} from "../context/DataProvider";
import {errors} from "../models/Constants";

interface SelectInput {
    selectCategory: { value: string }
}

export default function SchedulePage() {
    const config = useContext(AuthContext).config
    const {setCurrentPage} = useContext(DataContext)
    const [scheduledDate, setScheduledDate] = useState<number>(1)
    const [description, setDescription] = useState<string>("")
    const [typeName, setTypeName] = useState<string>("Payment")
    const [amount, setAmount] = useState<number>(1)
    const [categoryError, setCategoryError] = useState<boolean>(false)
    const [descriptionError, setDescriptionError] = useState<boolean>(false)
    const [amountError, setAmountError] = useState<boolean>(false)
    const [scheduledError, setScheduledError] = useState<boolean>(false)


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

    useEffect(() => {
        setCurrentPage("Series")
    }, [setCurrentPage])

    const addSeries = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()

        if (!scheduledDate) {
            setScheduledError(true)
            return
        }
        if (!description) {
            setDescriptionError(true)
            return
        }
        if (!amount) {
            setAmountError(true)
            return
        }

        if (typeName === "Payment") {
            const form = event.currentTarget
            const formElements = form.elements as typeof form.elements & SelectInput
            const categoryId: string = formElements.selectCategory.value

            if (!categoryId || !categoryId.length) {
                setCategoryError(true)
                return
            }

            const payment: PaymentDTO = {description, amount, categoryId: categoryId}
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

    const onSchedulingDateChange = (event: ChangeEvent<HTMLInputElement>) => {
        const sDate: number = parseInt(event.target.value)
        if (sDate >= 0 && sDate <= 31) {
            setScheduledDate(sDate)
        }
    }
    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.value) {
            const _amount: number = parseInt(event.target.value)
            if (_amount >= 0 || !_amount) {
                setAmount(_amount)
            }
        }
    }
    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())
    const onTypeChange = (event: ChangeEvent<HTMLInputElement>) => {
        setTypeName(event.target.value)
        document.getElementById("selectCategory")!.hidden = event.target.value !== "Payment";
    }

    return (
        <div className={"schedulingPage"}>
            <div className={"series"}>
                <form onSubmit={addSeries} className={"addSeries"}>
                    <h2>Scheduled Day</h2>
                    {scheduledError ? <Alert severity={"error"}
                                             onClick={() => setScheduledError(false)}>{errors.description}</Alert> : null}
                    <InputBox type={"number"} onChange={onSchedulingDateChange} placeholder={"Scheduling Date"}
                              value={scheduledDate} min={1} max={31} step={1}/>
                    <h2>{typeName}</h2>
                    <div className={"roleCheck"}>
                        <input type={"radio"} id={"typePayment"} name={"type"} onChange={onTypeChange} value={"Payment"}
                               defaultChecked={true}/>
                        <label htmlFor="typePayment">Payment</label>
                        <input type={"radio"} id={"typeDeposit"} name={"type"} onChange={onTypeChange}
                               value={"Deposit"}/>
                        <label htmlFor="typeDeposit">Deposit</label>
                    </div>
                    {descriptionError ? <Alert severity={"error"}
                                               onClick={() => setDescriptionError(false)}>{errors.description}</Alert> : null}
                    <InputBox type="text" id={"description"} onChange={onDescriptionChange} value={description}
                              placeholder={"Description"}/>
                    {amountError ?
                        <Alert severity={"error"} onClick={() => setAmountError(false)}>{errors.amount}</Alert> : null}
                    <InputBox type={"number"} id={"amount"} onChange={onAmountChange} placeholder={"Amount"}
                              value={amount}
                              step={0.01}/>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DateRangePicker
                            value={rangeValue}
                            inputFormat={"dd/MM/yyyy"}
                            onChange={(newValue) => {
                                setRangeValue(newValue);
                            }}
                            renderInput={(startProps, endProps) => (
                                <React.Fragment>
                                    <TextField {...startProps}/>
                                    <Box sx={{mx: 2}}> to </Box>
                                    <TextField {...endProps}/>
                                </React.Fragment>
                            )}
                        />
                    </LocalizationProvider>

                    {categoryError ? <Alert severity={"error"}
                                            onClick={() => setCategoryError(false)}>{errors.category}</Alert> : null}
                    <select name="Category" id="selectCategory">
                        {
                            categories.map((category, index) =>
                                <option value={category.categoryId} key={index}>{category.categoryName}</option>
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
