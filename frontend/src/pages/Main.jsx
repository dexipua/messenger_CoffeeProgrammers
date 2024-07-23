import SideBar from "../components/layouts/SideBar";
import {Box} from "@mui/material";
import ContentSection from "../components/layouts/ContentSection";
import {useState} from "react";

const Main = () => {
    const [selectedChatId, setSelectedChatId] = useState(null)

    return (
        <Box sx={{display: 'flex'}}>
            <SideBar selectChatId={setSelectedChatId}/>
            <ContentSection selectedChatId={selectedChatId}/>
        </Box>

    )
}

export default Main;