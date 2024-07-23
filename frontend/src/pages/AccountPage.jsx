import React, {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import {Box, Paper} from "@mui/material";
import {useNavigate, useParams} from "react-router-dom";
import AccountService from "../services/AccountService";

const AccountPage = () => {
    const {id} = useParams();

    const [description, setDescription] = useState();
    const [email, setEmail] = useState();
    const [firstName, setFirstName] = useState();
    const [lastName, setLastName] = useState();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(false)

    const get = async () => {
        try {
            const response = await AccountService.getById(id)
            console.log(id)
            console.log('Do successful:', response);
            setFirstName(response.firstName)
            setLastName(response.lastName)
            setEmail(response.email)
            setDescription(response.description)
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        get();
    }, []);

    if (loading) {
        <p>Loading...</p>
    }

    if (error) {
        <p>Error</p>
    }

    return (
        <Box
            height="100vh"
            component="div"
            display="flex"
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
        >
            <Paper sx={{backgroundColor: "lightblue", width: 350, padding: 3}}>
                <Typography variant="h2" component="h2">
                    Account
                </Typography>
                <Typography variant="h4" component="h4">
                    {firstName} {lastName}
                </Typography>
                <Typography variant="h4" component="h4">
                    {email}
                </Typography>
                <Typography variant="p" component="p">
                    {description}
                </Typography>
            </Paper>
        </Box>
    );
};

export default AccountPage;
