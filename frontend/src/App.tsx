import React from 'react';
import './App.scss';
import {HashRouter, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage";
import NavBar from "./components/NavBar";
import AuthProvider from "./context/AuthProvider";
import LoginPage from "./pages/LoginPage";
import RequireAuth from "./components/RequireAuth";
import CategoryPage from "./pages/CategoryPage";
import ChangePaymentPage from "./pages/ChangePaymentPage";
import AdminPage from "./pages/AdminPage";
import RequireAdmin from "./components/RequireAdmin";
import DataProvider from "./context/DataProvider";
import RenameCategoryPage from "./pages/RenameCategoryPage";

function App() {
    return (
        <div className="App">
            <HashRouter>
                <AuthProvider>
                    <DataProvider>
                        <div className={"authDiv"}>
                            <NavBar/>
                            <Routes>
                                <Route path={"/login"} element={<LoginPage/>}/>
                                <Route path={"*"} element={
                                    <RequireAuth>
                                        <HomePage/>
                                    </RequireAuth>}
                                />
                                <Route path={"/categories"} element={
                                    <RequireAuth>
                                        <CategoryPage/>
                                    </RequireAuth>}
                                />
                                <Route path={"/changePayment/:categoryID/:paymentID"} element={
                                    <RequireAuth>
                                        <ChangePaymentPage/>
                                    </RequireAuth>}
                                />
                                <Route path={"/renameCategory/:categoryID/:categoryName"} element={
                                    <RequireAuth>
                                        <RenameCategoryPage/>
                                    </RequireAuth>}
                                />
                                <Route path={"/admin"} element={
                                    <RequireAuth>
                                        <RequireAdmin>
                                            <AdminPage/>
                                        </RequireAdmin>
                                    </RequireAuth>
                                }
                                />
                            </Routes>
                        </div>
                    </DataProvider>
                </AuthProvider>
            </HashRouter>
        </div>
    );
}

export default App;
