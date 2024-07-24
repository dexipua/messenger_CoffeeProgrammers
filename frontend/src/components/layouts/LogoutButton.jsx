import React from "react";
import Cookies from "js-cookie";
import {Box} from "@mui/material";
import {useNavigate} from "react-router-dom";
import IconButton from "@mui/material/IconButton";
import LogoutIcon from '@mui/icons-material/Logout';

const LogoutButton = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        Cookies.remove('id');
        Cookies.remove('token');
        Cookies.remove('firstName');
        Cookies.remove('lastName');
        Cookies.remove('email');

        navigate('/login');
    };

    return (
        <Box m={1} sx={{ display: 'flex', justifyContent: 'flex-start' }}>
            <IconButton size="small" onClick={handleLogout}>
                <LogoutIcon />
            </IconButton>
        </Box>
    );
};

export default LogoutButton;
