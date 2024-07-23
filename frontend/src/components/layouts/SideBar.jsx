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
import axios from "axios";
import Cookies from "js-cookie";
import {AppBar, Drawer} from "@mui/material";

// const drawerWidth = 240;
//
// const openedMixin = (theme) => ({
//     width: drawerWidth,
//     transition: theme.transitions.create('width', {
//         easing: theme.transitions.easing.sharp,
//         duration: theme.transitions.duration.enteringScreen,
//     }),
//     overflowX: 'hidden',
// });
//
// const closedMixin = (theme) => ({
//     transition: theme.transitions.create('width', {
//         easing: theme.transitions.easing.sharp,
//         duration: theme.transitions.duration.leavingScreen,
//     }),
//     overflowX: 'hidden',
//     width: `calc(${theme.spacing(7)} + 1px)`,
//     [theme.breakpoints.up('sm')]: {
//         width: `calc(${theme.spacing(8)} + 1px)`,
//     },
// });
//
// const DrawerHeader = styled('div')(({ theme }) => ({
//     display: 'flex',
//     alignItems: 'center',
//     justifyContent: 'flex-end',
//     padding: theme.spacing(0, 1),
//     // necessary for content to be below app bar
//     ...theme.mixins.toolbar,
// }));
//
// const AppBar = styled(MuiAppBar, {
//     shouldForwardProp: (prop) => prop !== 'open',
// })(({ theme, open }) => ({
//     zIndex: theme.zIndex.drawer + 1,
//     transition: theme.transitions.create(['width', 'margin'], {
//         easing: theme.transitions.easing.sharp,
//         duration: theme.transitions.duration.leavingScreen,
//     }),
//     ...(open && {
//         marginLeft: drawerWidth,
//         width: `calc(100% - ${drawerWidth}px)`,
//         transition: theme.transitions.create(['width', 'margin'], {
//             easing: theme.transitions.easing.sharp,
//             duration: theme.transitions.duration.enteringScreen,
//         }),
//     }),
// }));
//
// const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
//     ({ theme, open }) => ({
//         width: drawerWidth,
//         flexShrink: 0,
//         whiteSpace: 'nowrap',
//         boxSizing: 'border-box',
//         ...(open && {
//             ...openedMixin(theme),
//             '& .MuiDrawer-paper': openedMixin(theme),
//         }),
//         ...(!open && {
//             ...closedMixin(theme),
//             '& .MuiDrawer-paper': closedMixin(theme),
//         }),
//     }),
// );

const drawerWidth = 300;

export default function SideBar() {
    const [myContacts, setMyContacts] = useState([])

    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)


    useEffect(() => {
        const getData = async () => {
            try {
                const token = Cookies.get('jwtToken');
                const response = await axios.get(
                    'http://localhost:8080/accounts/getAllAccounts/1',
                    {
                        withCredentials: true,
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    }
                );
                console.log(response);
                setMyContacts(response.data);
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
                    {myContacts.map((contact) => (
                        <ListItem key={contact.id} disablePadding>
                            <ListItemButton>
                                <ListItemIcon>
                                    <MyAvatar name={contact.firstName + " " + contact.lastName}/>
                                </ListItemIcon>
                                <ListItemText primary={contact.firstName + " " + contact.lastName}/>
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            </Drawer>
        </Box>
    );
}
