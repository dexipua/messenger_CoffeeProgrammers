import {TextField} from "@mui/material";
import Box from "@mui/material/Box";
import * as React from "react";
import SendRoundedIcon from '@mui/icons-material/SendRounded';
import IconButton from "@mui/material/IconButton";

const SendMessageBar = ({message, changeMessage, sendMessage}) => {
    return (
        <Box
            sx={{
                position: 'fixed',
                bottom: 0,
                right: 0,
                left: 260,
                bgcolor: 'background.paper',
                p: 1,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                gap: 1,
                borderTop: '1px solid #ddd'
            }}
        >
            <TextField
                sx={{
                  width: '400px'
                }}
                onChange={(e) => changeMessage(e.target.value)}
                value={message}
                id="filled-basic"
                label="Message"
                variant="outlined"
                multiline
                maxRows={4}
            />
            <IconButton onClick={sendMessage} color="primary" disabled={message.trim() === ""}>
                <SendRoundedIcon/>
            </IconButton>
        </Box>
    )
}

export default SendMessageBar;
