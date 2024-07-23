import React, {useState} from "react";
import Typography from "@mui/material/Typography";
import {Box, Button, Paper, TextField} from "@mui/material";
import Cookies from 'js-cookie';
import {useNavigate} from "react-router-dom";

const Reg = () => {
        const [email, setEmail] = useState('');
        const navigate = useNavigate();

        const [loading, setLoading] = useState(true)

        const [error, setError] = useState(null)

        const handleSubmit = async (event) => {
            event.preventDefault();
            try {
                const response = await fetch
                ('http://localhost:8080/api/registration', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({message: email}),
                });
                const data = await response.json();
                console.log('Reg successful:', data);
                if (response.status === 200) {
                    Cookies.set('email', email, {expires: 1});
                    navigate('/regVer');
                }
            } catch
                (error) {
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
                        Registration
                    </Typography>
                    <Box
                        component="form"
                        onSubmit={handleSubmit}
                        display="flex"
                        flexDirection="column"
                        alignItems="center"
                        sx={{mt: 1}}
                    >
                        <TextField
                            label="Email"
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            fullWidth
                            margin="normal"
                        />
                        <Button type="submit" variant="contained" color="primary" sx={{mt: 2}}>
                            Regis
                        </Button>
                    </Box>
                </Paper>
            </Box>
        );
    }
;

export default Reg;
