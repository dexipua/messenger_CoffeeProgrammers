import * as React from "react";
import {useState} from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import MyAvatar from "../../layouts/MyAvatar";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";
import List from "@mui/material/List";
import Cookies from "js-cookie";
import InfoRoundedIcon from '@mui/icons-material/InfoRounded';
import IconButton from "@mui/material/IconButton";
import {Box} from "@mui/material";
import ChatBox from "../ChatBox";

const MyChats = ({chats, selectChatId, handleDeleteChat}) => {
    const [selectedChatId, setSelectedChatId] = useState(null)

    const getName = (chat) => {
        return chat.accounts.length === 2 ? (
            chat.accounts[0].id.toString() !== Cookies.get('id') ?
                (
                    chat.accounts[0].firstName + " " + chat.accounts[0].lastName
                ) : (
                    chat.accounts[1].firstName + " " + chat.accounts[1].lastName)
        ) : (
            chat.name
        )
    }

    const getEmail = (chat) => {
        return chat.accounts.length === 2 ? (
            chat.accounts[0].id.toString() !== Cookies.get('id') ?
                (
                    chat.accounts[0].email
                ) : (
                    chat.accounts[1].email
                )
        ) : (
            "Chat"
        )
    }

    return (
        <>
            {selectedChatId ? (
                <ChatBox
                    chatId={selectedChatId}
                    selectedChatId={setSelectedChatId}
                    handleDelete={handleDeleteChat}
                />
            ) : (
                <List>
                    {chats.map((chat) => (
                        <ListItem key={chat.id} disablePadding>
                            <ListItemButton onClick={() =>
                                selectChatId(chat.id)
                            }
                            >
                                <Box mr={-1.5}>
                                    <ListItemIcon>
                                        <MyAvatar name={getName(chat)}/>
                                    </ListItemIcon>
                                </Box>
                                <ListItemText>
                                    <Typography variant="body2" mb={-1} noWrap>
                                        {getName(chat)}
                                    </Typography>
                                    <Typography variant="caption">
                                        {getEmail(chat)}
                                    </Typography>
                                </ListItemText>
                            </ListItemButton>
                            <Box mr={-2}>
                                <ListItemIcon>
                                    <IconButton onClick={() => setSelectedChatId(chat.id)} size="small" edge="end">
                                        <InfoRoundedIcon/>
                                    </IconButton>
                                </ListItemIcon>
                            </Box>
                        </ListItem>
                    ))}
                </List>
            )
            }
        </>
    )
}

export default MyChats;