import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Main from "./pages/Main";
import AllAccounts from "./components/common/AllAccounts";
import Login from "./security/Login";
import Registration from "./security/Registration";
import PrivateRoute from "./security/PrivateRoute";

function App() {
    return (
        <Router>
            <Routes>
                <Route element={<PrivateRoute />}>
                    <Route exact path={"/"} element={<Main/>}/>
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route exact path={'/getAllAccounts'} element={<AllAccounts/>}/>
                </Route>

                <Route exact path={"/login"} element={<Login/>}/>
                <Route exact path={'/registration'} element={<Registration/>}/>

            </Routes>
        </Router>
    );
}

export default App;
