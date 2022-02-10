import {Navigate, useNavigate, useParams} from "react-router-dom";
import React, {ChangeEvent, FormEvent, useContext, useMemo, useState} from "react";
import {ICategoryController} from "../models/ControllerTypes";
import {AuthContext} from "../context/AuthProvider";
import './ChangePaymentPage.scss'
import CategoryController from "../controllers/CategoryController";

export default function RenameCategoryPage() {
    const urlParams = useParams()
    const categoryID = urlParams.categoryID
    const [categoryName, setCategoryName] = useState<string | undefined>(urlParams.categoryName)
    const config = useContext(AuthContext).config
    const controller: ICategoryController = useMemo(() => CategoryController(config), [config])
    const navigate = useNavigate()

    if (!categoryID || !categoryName) return <Navigate to="/categories"/>

    const changeCategory = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (categoryName && categoryName.length) {
            controller.renameCategory({categoryID, categoryName}).then(() => navigate("/categories"))
        }
    }

    const onNameChange = (event: ChangeEvent<HTMLInputElement>) => setCategoryName(event.target.value.trim())

    return (
        <div className={"changeCategoryPage"}>
            <h1>Change Category</h1>
            <form onSubmit={changeCategory}>
                <h2>Category name</h2>
                <input type="text" id={"categoryName"} onChange={onNameChange} value={categoryName}/>
                <input type="submit" value={"Change"}/>
            </form>
        </div>
    )
}