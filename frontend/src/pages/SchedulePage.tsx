import React, {ChangeEvent, ChangeEventHandler, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import SeriesController from "../controllers/SeriesController";
import {Series} from "../models/Series";
import {PaymentDTO} from "../models/Payment";
import SeriesItems from "../components/SeriesItems";
import {ICategoryController, ISeriesController} from "../models/ControllerTypes";
import {AuthContext} from "../context/AuthProvider";
import './SchedulingPage.scss'
import CategoryController from "../controllers/CategoryController";
import {Category} from "../models/Category";

export default function SchedulePage() {
    const config = useContext(AuthContext).config
    const [series, setSeries] = useState<Series[]>([])
    const [scheduledDate, setScheduledDate] = useState<number>(1)
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(1)
    const [categories, setCategories] = useState<Category[]>([])
    const [categoryID, setCategoryID] = useState<string>("")
    const seriesController: ISeriesController = useMemo(() => SeriesController(config), [config])
    const categoryController: ICategoryController = useMemo(() => CategoryController(config), [config])

    useEffect(() => {
        seriesController.getSeries().then(setSeries)
        categoryController.getCategories().then(setCategories)
    }, [seriesController, categoryController])

    const addSeries = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const payment: PaymentDTO = {description, amount, categoryID}
        const seriesObj: Series = {scheduledDate, payment}
        seriesController.addSeries(seriesObj).then(setSeries)
    }

    const onSchedulingDateChange = (event: ChangeEvent<HTMLInputElement>) => setScheduledDate(parseInt(event.target.value))
    const onAmountChange = (event: ChangeEvent<HTMLInputElement>) => setAmount(parseInt(event.target.value))
    const onDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => setDescription(event.target.value.trim())
    const onSelectChange = (event: ChangeEvent<HTMLSelectElement>) => setCategoryID(event.target.value)


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
                    <input type="text" id={"description"} onChange={onDescriptionChange} value={description} placeholder={"Description"}/>
                    <input type="number" id={"amount"} onChange={onAmountChange} placeholder={"Amount"} value={amount} step={0.01}/><br/>
                    <select name="Category" id="selectCategory" value={categoryID} onChange={onSelectChange}>
                        {
                            categories.map((category, index) =>
                                <option value={category.categoryID} key={index}>{category.categoryName}</option>
                            )
                        }
                    </select>
                    <input type="submit" value={"Add Series"}/>
                </form>
                <SeriesItems series={series}/>
            </div>
        </div>
    )
}