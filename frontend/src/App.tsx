import React from 'react';
import './App.scss';
import {HashRouter, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage";
import NavBar from "./components/navbar/NavBar";
import AuthProvider from "./context/AuthProvider";
import LoginPage from "./pages/LoginPage";
import RequireAuth from "./components/RequireAuth";
import CategoryPage from "./pages/CategoryPage";
import ChangePaymentPage from "./pages/ChangePaymentPage";
import AdminPage from "./pages/AdminPage";
import RequireAdmin from "./components/RequireAdmin";
import DataProvider from "./context/DataProvider";
import RenameCategoryPage from "./pages/RenameCategoryPage";
import SchedulePage from "./pages/SchedulePage";
import DepositPage from "./pages/DepositPage";
import ChangeDepositPage from "./pages/ChangeDepositPage";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";

const theme = createTheme({
    palette: {
        mode: 'dark',
        background: {default: '#282c34'}
    },
})

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
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
                                    <Route path={"/series"} element={
                                        <RequireAuth>
                                            <SchedulePage/>
                                        </RequireAuth>
                                    }
                                    />
                                    <Route path={"/deposits"} element={
                                        <RequireAuth>
                                            <DepositPage/>
                                        </RequireAuth>
                                    }
                                    />
                                    <Route path={"/deposits/change/:depositId"} element={
                                        <RequireAuth>
                                            <ChangeDepositPage/>
                                        </RequireAuth>}
                                    />
                                </Routes>
                            </div>
                        </DataProvider>
                    </AuthProvider>
                </HashRouter>
            </div>
        </ThemeProvider>
    );
}

export default App;
