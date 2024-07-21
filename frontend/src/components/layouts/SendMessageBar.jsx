import {TextField} from "@mui/material";
import Box from "@mui/material/Box";
import * as React from "react";
import SendRoundedIcon from '@mui/icons-material/SendRounded';
import IconButton from "@mui/material/IconButton";

const SendMessageBar = () => {
    return (
        <Box
            sx={{
                position: 'fixed',
                bottom: 0,
                left: 0,
                right: 0,
                width: '100%',
                bgcolor: 'background.paper',
                p: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                gap: 1,
            }}
        >
            <TextField
                sx={{
                    width: '400px'
                }}
                id="filled-basic"
                label="Message"
                variant="outlined"
                multiline
                maxRows={4}
            />
            <IconButton>
                <SendRoundedIcon />
            </IconButton>
        </Box>
    )
}

export default SendMessageBar;