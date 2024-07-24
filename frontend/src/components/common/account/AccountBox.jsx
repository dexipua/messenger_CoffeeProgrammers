import React, {useEffect, useState} from "react";
import {Box, Button, IconButton, List, ListItem, TextField} from "@mui/material";
import AccountService from "../../../services/AccountService";
import Cookies from "js-cookie";
import EditIcon from '@mui/icons-material/Edit';
import CancelIcon from '@mui/icons-material/Cancel';
import MyAvatar from "../../layouts/MyAvatar";
import DeleteButton from "../../layouts/delete/DeleteButton";
import EmailRoundedIcon from '@mui/icons-material/EmailRounded';
import Typography from "@mui/material/Typography";

const AccountBox = ({id, handleDelete, selectContactId, writeToContact}) => {
    const myId = Cookies.get("id")
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [description, setDescription] = useState("");

    const [newFirstName, setNewFirstName] = useState("");
    const [newLastName, setNewLastName] = useState("");
    const [newDescription, setNewDescription] = useState("");

    const [isMyAccount, setIsMyAccount] = useState(false);
    const [isEditing, setIsEditing] = useState(false);

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
        setIsEditing(false);
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
            setIsEditing(false);
        } catch (error) {
            setError(error.response.data.messages);
        }
    };

    const removeContact = async () => {
        try {
            await AccountService.removeFromContact(myId, id);
            handleDelete(id)
            selectContactId(null)
        } catch (error) {
            console.error("Failed to remove contact:", error);
        }
    };

    const toggleEditMode = () => {
        setIsEditing(prev => !prev);
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <List>
            <ListItem>
                <Box display="flex" alignItems="center" gap={1} mb={-1}>
                    <MyAvatar name={firstName + " " + lastName}/>
                    {isMyAccount ? (
                        <>
                            <IconButton onClick={toggleEditMode} size="small">
                                {isEditing ? <CancelIcon/> : <EditIcon/>}
                            </IconButton>
                        </>
                    ) : (
                        <>
                            <IconButton onClick={writeToContact} edge="end">
                                <EmailRoundedIcon/>
                            </IconButton>
                            <IconButton edge="end">
                                <DeleteButton
                                    text={"Are you sure you want to delete this contact?"}
                                    deleteFunction={() => removeContact()}
                                />
                            </IconButton>

                        </>
                    )}
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
            {!error ? "" : error.map(er =>
                <Typography variant="body2" color="textSecondary" align="center">
                    {er}
                </Typography>
            )}
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
