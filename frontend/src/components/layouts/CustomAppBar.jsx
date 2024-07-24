import * as React from "react";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import {AppBar} from "@mui/material";

const drawerWidth = 260;

const CustomAppBar = () => {
    return (
        <AppBar
            position="fixed"
            sx={{width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px`}}
        >
            <Toolbar>
                <Typography variant="h6" noWrap component="div">
                    Todo
                </Typography>
            </Toolbar>
        </AppBar>
    )
}

export default CustomAppBar;