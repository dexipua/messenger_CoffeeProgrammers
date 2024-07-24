import * as React from 'react';
import {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import {Drawer} from "@mui/material";
import ChatService from "../../services/ChatService";
import MyChats from "../common/account/MyChats";
import CustomAppBar from "./CustomAppBar";
import AccountService from "../../services/AccountService";
import MyContacts from "../common/account/MyContacts";
import MenuRoundedIcon from '@mui/icons-material/MenuRounded';
import IconButton from "@mui/material/IconButton";
import TabsMenu from "../common/TabsMenu";
import AccountBox from "../common/account/AccountBox";
import Cookies from "js-cookie";

const drawerWidth = 260;

const drawerStyles = {
    width: drawerWidth,
    flexShrink: 0,
    '& .MuiDrawer-paper': {
        width: drawerWidth,
        boxSizing: 'border-box',
    },
};

export default function SideBar({selectChatId}) {
    const [myChats, setMyChats] = useState([])
    const [myContacts, setMyContacts] = useState([])

    const [tab, setTab] = useState("MENU")

    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    const accountId = Cookies.get("id")
    useEffect(() => {
        const getData = async () => {
            try {
                const [response1, response2] = await Promise.all([
                    ChatService.getAllByUserId(),
                    AccountService.getAllContacts(),
                ]);

                setMyChats(response1);
                setMyContacts(response2);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };

        getData();
    }, []);

    const handleDeleteFromContact = (contactId) => {
        setMyContacts(myContacts.filter(contact => contact.id !== contactId));
    }

    const handleAddChatToList = (newChat) => {
        setMyChats((prevChats) => [...prevChats, newChat]);
    };

    const handleDeleteChat = (chatId) => {
        setMyChats(myChats.filter(chat => chat.id !== chatId));
    }
    let tabContent;
    switch (tab) {
        case "MENU":
            tabContent = <TabsMenu selectTab={setTab}/>;
            break;
        case "CHATS":
            tabContent =
                <MyChats
                    chats={myChats}
                    selectChatId={selectChatId}
                    handleDeleteChat={handleDeleteChat}
                />;
            break;
        case "CONTACTS":
            tabContent = <MyContacts
                contacts={myContacts}
                selectChatId={selectChatId}
                handleDelete={handleDeleteFromContact}
                addChatToList={handleAddChatToList}
            />;
            break;
        case "MY_ACCOUNT":
            tabContent = <AccountBox id={accountId}/>;
            break;
        case "ACCOUNT":
            tabContent = <AccountBox id={accountId}/>;
            break;
        default:
            tabContent = <div>Something went wrong</div>
            break;
    }

    if (loading) {
        return <div>Loading...</div>
    }

    if (error) {
        return <div>Error: {error}</div>
    }

    return (
        <Box>
            <CssBaseline/>
            <CustomAppBar/>
            <Drawer sx={drawerStyles} variant="permanent" anchor="left">
                <Toolbar>
                    <IconButton edge="start"
                                onClick={() => setTab("MENU")}
                    >
                        <MenuRoundedIcon/>
                    </IconButton>
                </Toolbar>
                <Divider/>
                {tabContent}
            </Drawer>
        </Box>
    );
}
