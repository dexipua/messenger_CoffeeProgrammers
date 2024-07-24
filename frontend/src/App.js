import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./pages/Login";
import Main from "./pages/Main";
import LoginVerification from "./pages/LoginVerefication";
import Reg from "./pages/Reg";
import RegVer from "./pages/RegVer";
import AllAccounts from "./pages/AllAccounts";
import AccountBox from "./components/common/account/AccountBox";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path={"/"} element={<Main/>}/>
                <Route exact path={"/login"} element={<Login/>}/>
                <Route exact path={'/loginVer'} element={<LoginVerification/>}/>
                <Route exact path={'/reg'} element={<Reg/>}/>
                <Route exact path={'/regVer'} element={<RegVer/>}/>
                <Route exact path={'/getAllAccounts'} element={<AllAccounts/>}/>
                <Route path={`/getById/:id`} element={<AccountBox/>}/>
            </Routes>
        </Router>
    );
}

export default App;
