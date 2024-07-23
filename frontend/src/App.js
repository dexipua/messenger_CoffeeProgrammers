import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./security/Login";
import Main from "./pages/Main";
import ChatRoomTest from "./pages/ChatRoomTest";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path={"/"} element={<Main/>}/>
                <Route exact path={"/login"} element={<Login/>}/>
                <Route exact path={"/chat"} element={<ChatRoomTest/>}/>
            </Routes>
        </Router>
    );
}

export default App;
