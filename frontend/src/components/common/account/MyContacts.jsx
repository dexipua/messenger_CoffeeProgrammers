import * as React from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import MyAvatar from "../../layouts/MyAvatar";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";
import List from "@mui/material/List";
import ChatService from "../../../services/ChatService";
import Cookies from "js-cookie";
import {Box} from "@mui/material";
import AccountBox from "./AccountBox";

const MyContacts = ({contacts, selectChatId, handleDelete}) => {
    const accountId = Cookies.get('id');
    const [selectedContactId, setSelectedContactId] = React.useState(null);

    const getFullName = (contact) => {
        return contact.firstName + " " + contact.lastName;
    };

    const getChat = async (contactId) => {
        const chatId = await ChatService.getByAccountIds(accountId, contactId);
        if (chatId === "") {
            const response = await ChatService.create(accountId, contactId, "Chat Room");
            selectChatId(response.id);
        } else {
            selectChatId(chatId);
        }
    };

    return (
        <>
            {selectedContactId ? (
                <AccountBox
                    id={selectedContactId}
                    handleDelete={handleDelete}
                    selectContactId={setSelectedContactId}
                />
            ) : (
                <List>
                    {contacts.map((contact) => (
                        <ListItem key={contact.id} disablePadding>
                            <ListItemButton onClick={() => setSelectedContactId(contact.id)}>
                                <Box mr={-1.5}>
                                    <ListItemIcon>
                                        <MyAvatar name={getFullName(contact)}/>
                                    </ListItemIcon>
                                </Box>
                                <ListItemText>
                                    <Typography variant="body2" mb={-1}>
                                        {getFullName(contact)}
                                    </Typography>
                                    <Typography variant="caption" color="text.secondary">
                                        {contact.email}
                                    </Typography>
                                </ListItemText>
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            )}
        </>
    );
};

export default MyContacts;
