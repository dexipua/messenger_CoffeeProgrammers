import React, {useEffect, useState} from "react";
import {Box, Button, IconButton, List, ListItem, TextField} from "@mui/material";
import AccountService from "../../../services/AccountService";
import Cookies from "js-cookie";
import EditIcon from '@mui/icons-material/Edit';
import CancelIcon from '@mui/icons-material/Cancel';
import MyAvatar from "../../layouts/MyAvatar";

const AccountBox = ({id}) => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [description, setDescription] = useState("");

    const [newFirstName, setNewFirstName] = useState("");
    const [newLastName, setNewLastName] = useState("");
    const [newDescription, setNewDescription] = useState("");

    const [isMyAccount, setIsMyAccount] = useState(false);
    const [isEditing, setIsEditing] = useState(false); // Додано стан для режиму редагування

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const get = async () => {
            try {
                const response = await AccountService.getById(id);

                setFirstName(response.firstName);
                setLastName(response.lastName);
                setEmail(response.email);
                setDescription(response.description);

                setNewFirstName(response.firstName);
                setNewLastName(response.lastName);
                setNewDescription(response.description);

                setIsMyAccount(response.id.toString() === Cookies.get('id'));
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        get();
    }, [id]);

    const handleCancelUpdate = () => {
        setNewFirstName(firstName);
        setNewLastName(lastName);
        setNewDescription(description);
        setIsEditing(false); // Вийти з режиму редагування
    };

    const handleConfirmUpdate = async () => {
        try {
            const response = await AccountService.update(
                newFirstName,
                newLastName,
                newDescription
            );

            setFirstName(response.firstName);
            setLastName(response.lastName);
            setDescription(response.description);
            setIsEditing(false); // Вийти з режиму редагування
        } catch (error) {
            setError(error.message);
        }
    };

    const toggleEditMode = () => {
        setIsEditing(prev => !prev);
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    return (
        <List>
            <ListItem>
                <Box display="flex" alignItems="center" gap={1} mb={-1}>
                    <MyAvatar name={firstName + " " + lastName}/>
                    {isMyAccount &&
                        <>
                            <IconButton onClick={toggleEditMode} size="small">
                                {isEditing ? <CancelIcon/> : <EditIcon/>}
                            </IconButton>
                        </>
                    }
                </Box>
            </ListItem>
            <ListItem>
                <Box mb={-1} width="100%">
                    <TextField
                        label="First Name"
                        value={isEditing ? newFirstName : firstName}
                        onChange={(e) => setNewFirstName(e.target.value)}
                        variant="standard"
                        fullWidth
                        disabled={!isEditing}
                    />
                </Box>
            </ListItem>
            <ListItem>
                <Box mb={-1} width="100%">
                    <TextField
                        label="Last Name"
                        value={isEditing ? newLastName : lastName}
                        onChange={(e) => setNewLastName(e.target.value)}
                        variant="standard"
                        fullWidth
                        disabled={!isEditing}
                    />
                </Box>
            </ListItem>
            <ListItem>
                <Box mb={-1} width="100%">
                    <TextField
                        label="Email"
                        value={email}
                        variant="standard"
                        fullWidth
                        disabled
                    />
                </Box>
            </ListItem>
            <ListItem>
                <Box mb={-1} width="100%">
                    <TextField
                        label="Description"
                        value={isEditing ? newDescription : description}
                        onChange={(e) => setNewDescription(e.target.value)}
                        variant="standard"
                        fullWidth
                        disabled={!isEditing}
                        multiline
                        maxRows={3}
                    />
                </Box>
            </ListItem>
            {
                isEditing &&
                <ListItem>
                    <Box display="flex" justifyContent="flex-start" gap={2} width="100%">
                        <Button variant="contained" onClick={handleCancelUpdate} color="error">
                            Cancel
                        </Button>
                        <Button variant="contained" onClick={handleConfirmUpdate}>
                            Save
                        </Button>
                    </Box>
                </ListItem>
            }
        </List>
    )
        ;
};

export default AccountBox;
