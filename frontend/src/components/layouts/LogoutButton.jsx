import React from "react";
import Cookies from "js-cookie";
import {Box, Button} from "@mui/material";
import {useNavigate} from "react-router-dom";

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
        <Box m={1}>
            <Button variant="text" size='medium' onClick={handleLogout}>
                Logout
            </Button>
        </Box>
    );
};

export default LogoutButton;
