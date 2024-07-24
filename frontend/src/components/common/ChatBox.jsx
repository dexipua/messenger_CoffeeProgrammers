import * as React from "react";
import {useEffect, useState} from "react";
import {Box, IconButton, List, ListItem, TextField} from "@mui/material";
import ChatService from "../../services/ChatService";
import MyAvatar from "../layouts/MyAvatar";
import DeleteButton from "../layouts/delete/DeleteButton";

const ChatBox = ({chatId, selectedChatId, handleDelete}) => {
    const [name, setName] = useState("")
    const [accounts, setAccounts] = useState([])

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const get = async () => {
            try {
                const response = await ChatService.getById(chatId);

                setName(response.name)
                setAccounts(response.accounts)

            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        get();
    }, [chatId]);

    const removeChat = async () => {
        try {
            await ChatService.delete(chatId)
            handleDelete(chatId)
            selectedChatId(null)
        } catch (error) {
            console.error("Failed to remove contact:", error);
        }
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
                <MyAvatar name={name}/>
                <IconButton edge="end">
                    <DeleteButton
                        text={"Are you sure you want to delete this chat?"}
                        deleteFunction={removeChat}
                    />
                </IconButton>
            </ListItem>
            <ListItem>
                <Box mb={-1} width="100%">
                    <TextField
                        label="Chat name"
                        value={name}
                        variant="standard"
                        fullWidth
                        disabled
                    />
                </Box>
            </ListItem>
            {accounts.map((account, index) => (
                <ListItem key={account.id}>
                    <Box mb={-1} width="100%">
                        <TextField
                            label={`Member #${index + 1}`}
                            value={account.firstName + " " + account.lastName}
                            variant="standard"
                            fullWidth
                            disabled
                        />
                    </Box>
                </ListItem>
            ))}
        </List>
    );
}

export default ChatBox;