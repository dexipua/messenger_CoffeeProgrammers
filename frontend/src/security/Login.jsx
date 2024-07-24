import React, {useState} from "react";
import {Box, Button, Container, TextField} from "@mui/material";
import {Link, useNavigate} from "react-router-dom";
import AuthService from "../services/AuthService";
import Typography from "@mui/material/Typography";

const formStyles = {
    display: 'flex',
    flexDirection: 'column',
    gap: 2,
    padding: 3,
    borderRadius: 1,
    boxShadow: 1,
    backgroundColor: '#fff',
    alignItems: 'center'
}

const mainBoxStyles = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    backgroundColor: '#f0f2f5'
}

const Login = () => {
    const [email, setEmail] = useState('am@gmail.com');
    const [password, setPassword] = useState('passWord1');
    const [code, setCode] = useState('12')

    const [isLogin, setIsLogin] = useState(false)

    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            let response = await AuthService.logValid(email, password, 0)
            response = await AuthService.login(email)
            setIsLogin(true)
            console.log('Login successful:', response);
        } catch (error) {
            setError(error.response.data.messages);
        } finally {
            setLoading(false);
        }
    };

    const handleLoginVerification = async (event) => {
        event.preventDefault();

        setLoading(true);

        try {
            const response = await AuthService.logVer(email, password, code);
            navigate('/')
            console.log('LoginVerification successful:', response);
        } catch (error) {
            setError(error.response.data.messages);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        <p>Loading...</p>
    }

    return (
        <Box sx={mainBoxStyles}>
            <Container maxWidth="xs">
                {!isLogin ? (
                    <Box
                        component="form"
                        onSubmit={handleLogin}
                        sx={formStyles}
                    >
                        <Typography variant="h6">
                            Login
                        </Typography>
                        <TextField
                            label="Email"
                            type="email"
                            variant="outlined"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            fullWidth
                        />
                        <TextField
                            label="Password"
                            type="password"
                            variant="outlined"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            fullWidth
                        />
                        {!error ? "" : error.map(er =>
                            <Typography variant="body2" color="textSecondary" align="center">
                                {er}
                            </Typography>
                        )}
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                        >
                            Login
                        </Button>
                        <Link to={'/registration'} style={{color: 'grey'}}>
                            <Typography >
                                Registration
                            </Typography>
                        </Link>
                    </Box>
                ) : (
                    <Box
                        component="form"
                        onSubmit={handleLoginVerification}
                        sx={formStyles}
                    >
                        <Typography variant="h6" mb={-2}>
                            Email Verification
                        </Typography>
                        <Typography variant="body2" color="textSecondary" align="center">
                            You will receive a verification code to your email. Please Check your inbox (and spam folder) for the code.
                        </Typography>
                        <TextField
                            label="Code"
                            type="text"
                            variant="outlined"
                            value={code}
                            onChange={(e) => setCode(e.target.value)}
                            fullWidth
                        />
                        {!error ? "" : error.map(er =>
                            <Typography variant="body2" color="textSecondary" align="center">
                                {er}
                            </Typography>
                        )}
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                        >
                            Check
                        </Button>
                    </Box>
                )}
            </Container>
        </Box>
    );
};

export default Login;