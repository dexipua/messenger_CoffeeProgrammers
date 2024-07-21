import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./pages/Login";
import ChatRoom from "./pages/ChatRoom";
import Main from "./pages/Main";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path={"/"} element={<Main/>}/>
                <Route exact path={"/login"} element={<Login/>}/>
                <Route exact path={"/chat"} element={<ChatRoom/>}/>

            </Routes>
        </Router>
    );
}

export default App;
