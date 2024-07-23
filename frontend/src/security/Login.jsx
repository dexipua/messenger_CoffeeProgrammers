import axios from 'axios';
import {useState} from "react";
import Cookies from 'js-cookie';
import {Box, Button, Container, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";

const Login = () => {
    const [email, setEmail] = useState('am@gmail.com');
    const [password, setPassword] = useState('passWord1');

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            console.log(email, password)
            const response = await axios.post(
                'http://localhost:8080/api/auth/login',
                {username: email, password:  password },
                { withCredentials: true }
            );

            if (response.data) {
                navigate("/")

                Cookies.set('jwtToken', response.data.token, { expires: 1 });
                Cookies.set('Account', response.data)
                console.log("Login successful");
                const token = Cookies.get('jwtToken');
                console.log("JWT_TOKEN", token)
            } else {
                console.log("Bad login")
            }
        } catch (error) {
            console.error('Login error:', error);
        }
    };

    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh', // Висота на весь екран
                backgroundColor: '#f0f2f5' // Світлий фон для кращої видимості
            }}
        >
            <Container maxWidth="xs">
                <Box
                    component="form"
                    onSubmit={handleLogin}
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        gap: 2,
                        padding: 3,
                        borderRadius: 1,
                        boxShadow: 1,
                        backgroundColor: '#fff'
                    }}
                >
                    <TextField
                        label="Email"
                        type="email"
                        variant="outlined"
                        value={email}
                        // onChange={(e) => setEmail(e.target.value)}
                        fullWidth
                    />
                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        value={password}
                        // onChange={(e) => setPassword(e.target.value)}
                        fullWidth
                    />
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                    >
                        Login
                    </Button>
                </Box>
            </Container>
        </Box>
    );
};

export default Login;