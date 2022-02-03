import React from 'react';
import './App.scss';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage";
import NavBar from "./components/NavBar";
import AuthProvider from "./context/AuthProvider";
import LoginPage from "./pages/LoginPage";
import RequireAuth from "./components/RequireAuth";
import CategoryPage from "./pages/CategoryPage";

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                {/*<NavBar/>*/}
                <AuthProvider>
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
                    </Routes>
                </AuthProvider>
            </BrowserRouter>
        </div>
    );
}

export default App;
