import React, {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import {Box, Dialog, DialogTitle, Paper, TextField} from "@mui/material";
import {useParams} from "react-router-dom";
import AccountService from "../services/AccountService";
import Cookies from "js-cookie";
import IconButton from "@mui/material/IconButton";
import EditIcon from '@mui/icons-material/Edit';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import CloseIcon from '@mui/icons-material/Close';

const AccountPage = () => {
    const { id } = useParams();

    const [open, setOpen] = useState(false);
    const [isMy, setIsMy] = useState(false);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [description, setDescription] = useState("");

    const [firstNameToUpdate, setFirstNameToUpdate] = useState("");
    const [lastNameToUpdate, setLastNameToUpdate] = useState("");
    const [descriptionToUpdate, setDescriptionToUpdate] = useState("");

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        const get = async () => {
            try {
                const response = await AccountService.getById(id);
                console.log(id);
                console.log('Do successful:', response);

                setFirstName(response.firstName);
                setLastName(response.lastName);
                setEmail(response.email);
                setDescription(response.description);
                setFirstNameToUpdate(response.firstName);
                setLastNameToUpdate(response.lastName);
                setDescriptionToUpdate(response.description);

                setIsMy(response.id.toString() === Cookies.get('id'));
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        get();
    }, [id]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleCloseUpdate = async () => {
        try {
            // Оновлення акаунта
            const response = await AccountService.update(firstNameToUpdate,
                lastNameToUpdate, descriptionToUpdate);
            console.log(id);
            console.log('Do successful:', response);

            // Оновлення стану тільки після успішного оновлення
            setFirstName(response.firstName);
            setLastName(response.lastName);
            setEmail(response.email);
            setDescription(response.description);
            setIsMy(response.id.toString() === Cookies.get('id'));
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
            setOpen(false); // Закриття діалогу
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    const updateDia = () => {
        if (isMy) {
            return (
                <Box>
                    <IconButton onClick={handleClickOpen}><EditIcon /></IconButton>
                    <Dialog
                        aria-labelledby="customized-dialog-title"
                        open={open}
                        sx={{ width: 400 }}
                    >
                        <Box>
                            <DialogTitle sx={{ m: 0, p: 2 }} id="customized-dialog-title">
                                Update
                            </DialogTitle>
                            <TextField
                                sx={{ margin: 2, width: 300 }}
                                label="First name"
                                type="text"
                                id="firstNameToUpdate"
                                value={firstNameToUpdate}
                                onChange={(e) => setFirstNameToUpdate(e.target.value)}
                                required
                                margin="normal"
                            />
                            <TextField
                                sx={{ margin: 2, width: 300 }}
                                label="Last name"
                                type="text"
                                id="lastNameToUpdate"
                                value={lastNameToUpdate}
                                onChange={(e) => setLastNameToUpdate(e.target.value)}
                                required
                                margin="normal"
                            />
                            <TextField
                                sx={{ margin: 2, width: 300 }}
                                label="Description"
                                type="text"
                                id="descriptionToUpdate"
                                value={descriptionToUpdate}
                                onChange={(e) => setDescriptionToUpdate(e.target.value)}
                                required
                                multiline
                                maxRows={3}
                                margin="normal"
                            />
                            <IconButton
                                aria-label="close"
                                onClick={handleClose}
                                sx={{
                                    position: 'absolute',
                                    right: 8,
                                    top: 8,
                                    color: (theme) => theme.palette.grey[500],
                                }}
                            >
                                <CloseIcon />
                            </IconButton>
                            <DialogActions>
                                <Button autoFocus onClick={handleCloseUpdate}>
                                    Save changes
                                </Button>
                            </DialogActions>
                        </Box>
                    </Dialog>
                </Box>
            );
        }
        return null;
    };

    return (
        <Box
            height="100vh"
            component="div"
            display="flex"
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
        >
            <Paper sx={{ backgroundColor: "lightblue", width: 350, padding: 3 }}>
                <Typography variant="h2" component="h2">
                    Account
                </Typography>
                <Typography variant="h4" component="h4">
                    {firstName} {lastName}
                </Typography>
                <Typography variant="h4" component="h4">
                    {email}
                </Typography>
                <Typography variant="body1" component="p">
                    {description}
                </Typography>
                {updateDia()}
            </Paper>
        </Box>
    );
};

export default AccountPage;
