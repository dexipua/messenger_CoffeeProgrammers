import React, {useState} from "react";
import Typography from "@mui/material/Typography";
import {Box, Button, Paper, TextField} from "@mui/material";
import Cookies from 'js-cookie';

const LoginVerification = () => {
    const email = Cookies.get('email');
    const [password, setPassword] = useState('');
    const [code, setCode] = useState('')

    const [loading, setLoading] = useState(true)

    const [error, setError] = useState(null)

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response =
                await fetch('http://localhost:8080/api/verification/logging?code='+code, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({username: email, password}),
            });
            if (!response) {
                throw new Error('Network response was not ok');
            }
            Cookies.set(response);
            const data = await response.json();
            console.log('Login successful:', data);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

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
                    Login
                </Typography>
                <Box
                    component="form"
                    onSubmit={handleSubmit}
                    display="flex"
                    flexDirection="column"
                    alignItems="center"
                    sx={{mt: 1}}
                >
                    <Typography component="h3">{email}</Typography>
                    <TextField
                        label="Password"
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Code"
                        type="text"
                        id="code"
                        value={code}
                        onChange={(e) => setCode(e.target.value)}
                        required
                        fullWidth
                        margin="normal"
                    />
                    <Button type="submit" variant="contained" color="primary" sx={{mt: 2}}>
                        Login
                    </Button>
                </Box>
            </Paper>
        </Box>
    );
};

export default LoginVerification;
