import SideBar from "../components/layouts/SideBar";
import {Box} from "@mui/material";
import ContentSection from "../components/layouts/ContentSection";

const Main = () => {
    return (
        <div>
            <Box sx={{ display: 'flex' }}>
                <SideBar/>
                <ContentSection/>
            </Box>
        </div>
    )
}

export default Main;