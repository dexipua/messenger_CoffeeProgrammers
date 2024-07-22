import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./pages/Login";
import ChatRoom from "./pages/ChatRoom";
import Main from "./pages/Main";
import LoginVerification from "./pages/LoginVerefication";
import Reg from "./pages/Reg";
import RegVer from "./pages/RegVer";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path={"/"} element={<Main/>}/>
                <Route exact path={"/login"} element={<Login/>}/>
                <Route exact path={"/chat"} element={<ChatRoom/>}/>
                <Route exact path={'/loginVer'} element={<LoginVerification/>}/>
                <Route exact path={'/reg'} element={<Reg/>}/>
                <Route exact path={'/regVer'} element={<RegVer/>}/>
            </Routes>
        </Router>
    );
}

export default App;
