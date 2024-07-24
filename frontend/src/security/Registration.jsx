import React, {useState} from "react";
import Typography from "@mui/material/Typography";
import {Box, Button, Container, TextField} from "@mui/material";
import AuthService from "../services/AuthService";
import {Link, useNavigate} from "react-router-dom";


const formStyles = {
    display: 'flex',
    flexDirection: 'column',
    gap: 2,
    padding: 3,
    borderRadius: 1,
    boxShadow: 1,
    backgroundColor: '#fff',
    alignItems: 'center'
};

const mainBoxStyles = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    backgroundColor: '#f0f2f5'
};

const Registration = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [description, setDescription] = useState('');
    const [code, setCode] = useState('');

    const [isRegister, setIsRegister] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleRegistrationSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        try {
             await AuthService.regValid(email, password,
                0, description, firstName, lastName);
            const response = await AuthService.reg(email, password)
            setIsRegister(true);
            console.log('Registration successful:', response);
        } catch (error) {
            setError(error.response.data.messages);
        } finally {
            setLoading(false);
        }
    };

    const handleRegistrationVerificationSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        try {
            const response = await AuthService.regVer(
                email,
                password,
                code,
                description,
                firstName,
                lastName
            );

            console.log('Verification successful:', response);
            navigate('/')
        } catch (error) {
            setError(error.response.data.messages);
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <p>Loading...</p>;

    return (
        <Box sx={mainBoxStyles}>
            <Container maxWidth="xs">
                {isRegister ? (
                    <Box
                        component="form"
                        onSubmit={handleRegistrationVerificationSubmit}
                        sx={formStyles}
                    >
                        <Typography variant="h6" mb={2}>
                            Email Verification
                        </Typography>
                        <Typography variant="body2" color="textSecondary" align="center" mb={2}>
                            You will receive a verification code to your email. Please check your inbox (and spam folder) for the code.
                        </Typography>
                        <TextField
                            label="Verification Code"
                            type="text"
                            value={code}
                            onChange={(e) => setCode(e.target.value)}
                            required
                            fullWidth
                        />
                        {!error ? "" : error.map(er =>
                            <Typography variant="body2" color="textSecondary" align="center">
                                {er}
                            </Typography>
                        )}
                        <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                            Verify Code
                        </Button>
                    </Box>
                ) : (
                    <Box
                        component="form"
                        onSubmit={handleRegistrationSubmit}
                        sx={formStyles}
                    >
                        <Typography variant="h6" mb={2}>
                            Registration
                        </Typography>
                        <TextField
                            label="Email"
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            fullWidth
                        />
                        <TextField
                            label="Password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            fullWidth
                        />
                        <TextField
                            label="First Name"
                            type="text"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            required
                            fullWidth
                        />
                        <TextField
                            label="Last Name"
                            type="text"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            required
                            fullWidth
                        />
                        <TextField
                            label="Description"
                            type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                            fullWidth
                            multiline
                            maxRows={3}
                        />
                        {!error ? "" : error.map(er =>
                            <Typography variant="body2" color="textSecondary" align="center">
                                {er}
                            </Typography>
                        )}
                        <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                            Register
                        </Button>
                        <Link to={'/login'} style={{color: 'grey'}}>
                            <Typography >
                                Login
                            </Typography>
                        </Link>
                    </Box>
                )}
            </Container>
        </Box>
    );
};

export default Registration;
