import React, {ChangeEvent, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import SeriesController from "../controllers/SeriesController";
import {ISeriesController, Series} from "../models/Series";
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

interface SelectInput {
    selectCategory: { value: string }
}

export default function SchedulePage() {
    const config = useContext(AuthContext).config
    const [series, setSeries] = useState<Series[]>([])
    const [scheduledDate, setScheduledDate] = useState<number>(1)
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(1)
    const [categories, setCategories] = useState<Category[]>([])
    const [rangeValue, setRangeValue] = useState<DateRange<Date>>([null, null])
    const seriesController: ISeriesController = useMemo(() => SeriesController(config), [config])
    const categoryController: ICategoryController = useMemo(() => CategoryController(config), [config])

    useEffect(() => {
        seriesController.getSeries().then(setSeries)
        categoryController.getCategories().then(setCategories)
    }, [seriesController, categoryController])

    const addSeries = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const form = event.currentTarget
        const formElements = form.elements as typeof form.elements & SelectInput
        const categoryID: string = formElements.selectCategory.value

        if (!categoryID || !categoryID.length) return

        const payment: PaymentDTO = {description, amount, categoryID}
        const seriesObj: Series = {scheduledDate, payment, startDate: rangeValue[0], endDate: rangeValue[1]}
        seriesController.addSeries(seriesObj).then(setSeries)
    }

    const deleteSeries = (seriesId: string | undefined) => {
        if (seriesId) {
            seriesController.deleteSeries(seriesId).then(setSeries)
        }
    }

    const onSchedulingDateChange = (event: ChangeEvent<HTMLInputElement>) => setScheduledDate(parseInt(event.target.value))
    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => setAmount(parseInt(event.target.value))
    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())

    return (
        <div className={"schedulingPage"}>
            <div className={"schedulingHeader"}>
                <h1>Scheduling Page</h1>
            </div>
            <div className={"series"}>
                <form onSubmit={addSeries} className={"addSeries"}>
                    <h2>Scheduled Day</h2>
                    <input type="number" onChange={onSchedulingDateChange} placeholder={"Scheduling Date"}
                           value={scheduledDate}/>
                    <h2>Payment</h2>
                    <input type="text" id={"description"} onChange={onDescriptionChange} value={description}
                           placeholder={"Description"}/>
                    <input type="number" id={"amount"} onChange={onAmountChange} placeholder={"Amount"} value={amount}
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
                    <Button submit={true} value={"Add Series"}/>
                </form>
                <SeriesItems series={series} deleteSeries={deleteSeries}/>
            </div>
        </div>
    )
}