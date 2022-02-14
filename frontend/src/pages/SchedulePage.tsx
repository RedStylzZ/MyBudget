import React, {ChangeEvent, FormEvent, useContext, useEffect, useMemo, useState} from "react";
import SeriesController from "../controllers/SeriesController";
import {Series} from "../models/Series";
import {PaymentDTO} from "../models/Payment";
import SeriesItems from "../components/SeriesItems";
import {ISeriesController} from "../models/ControllerTypes";
import {AuthContext} from "../context/AuthProvider";
import './SchedulingPage.scss'

export default function SchedulePage() {
    const config = useContext(AuthContext).config
    const [series, setSeries] = useState<Series[]>([])
    const [scheduledDate, setScheduledDate] = useState<number>(1)
    const [description, setDescription] = useState<string>("")
    const [amount, setAmount] = useState<number>(1)
    const controller: ISeriesController = useMemo(() => SeriesController(config), [config])

    useEffect(() => {
        controller.getSeries().then(setSeries)
    }, [controller])

    const addSeries = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const payment: PaymentDTO = {description, amount}
        const seriesObj: Series = {scheduledDate, payment}
        controller.addSeries(seriesObj).then(setSeries)
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
                    <input type="number" onChange={onSchedulingDateChange} placeholder={"Scheduling Date"}
                           value={scheduledDate}/>
                    <h2>Payment</h2>
                    <input type="text" id={"description"} onChange={onDescriptionChange} value={description} placeholder={"Description"}/>
                    <input type="number" id={"amount"} onChange={onAmountChange} placeholder={"Amount"} value={amount} step={0.01}/>
                    <input type="submit" value={"Add Series"}/>
                </form>
                <SeriesItems series={series}/>
            </div>
        </div>
    )
}