import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./pages/Login";
import Main from "./pages/Main";
import LoginVerification from "./pages/LoginVerefication";
import Reg from "./pages/Reg";
import RegVer from "./pages/RegVer";
import Chat from "./components/common/Chat";
import AllAccounts from "./pages/AllAccounts";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path={"/"} element={<Main/>}/>
                <Route exact path={"/login"} element={<Login/>}/>
                <Route exact path={"/chat"} element={<Chat/>}/>
                <Route exact path={'/loginVer'} element={<LoginVerification/>}/>
                <Route exact path={'/reg'} element={<Reg/>}/>
                <Route exact path={'/regVer'} element={<RegVer/>}/>
                <Route exact path={'/getAllAccounts'} element={<AllAccounts/>}/>
            </Routes>
        </Router>
    );
}

export default App;
