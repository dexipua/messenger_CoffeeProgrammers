import * as React from 'react';
import {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MyAvatar from "./MyAvatar";
import {AppBar, Drawer} from "@mui/material";
import ChatService from "../../services/ChatService";

const drawerWidth = 300;

export default function SideBar({selectChatId}) {
    const [myChats, setMyChats] = useState([])

    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)


    useEffect(() => {
        const getData = async () => {
            try {
                const response = await ChatService.getAllByUserId()

                setMyChats(response);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };

        getData();
    }, []);


    if (loading) {
        return <div>Loading...</div>
    }

    if (error) {
        return <div>Error: {error}</div>
    }

    return (
        <Box>
            <CssBaseline/>
            <AppBar
                position="fixed"
                sx={{width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px`}}
            >
                <Toolbar>
                    <Typography variant="h6" noWrap component="div">
                        Todo
                    </Typography>
                </Toolbar>
            </AppBar>
            <Drawer
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                    },
                }}
                variant="permanent"
                anchor="left"
            >
                <Toolbar/>
                <Divider/>
                <List>
                    {myChats.map((chat) => (
                        <ListItem key={chat.id} disablePadding>
                            <ListItemButton onClick={() =>
                            {
                                selectChatId(chat.id)
                                console.log("Selected", chat.id)
                            }}
                            >
                                <ListItemIcon>
                                    <MyAvatar name={"Chat" + " " + chat.id}/>
                                </ListItemIcon>
                                <ListItemText primary={"Chat"}/>
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            </Drawer>
        </Box>
    );
}
