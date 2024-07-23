import React, {useEffect, useRef, useState} from 'react';
import {Client} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import ListItemText from "@mui/material/ListItemText";
import ListItem from "@mui/material/ListItem";
import {Box, Button, Container, TextField} from "@mui/material";
import List from "@mui/material/List";


const ChatRoom = () => {
    const [privateChats, setPrivateChats] = useState(new Map());
    const [publicChats, setPublicChats] = useState([]);

    const [tab, setTab] = useState("CHATROOM");
    const [userData, setUserData] = useState({
        username: '',
        receivername: '',
        connected: false,
        message: ''
    });

    const stompClientRef = useRef(null);

    useEffect(() => {
        console.log(userData);
    }, [userData]);

    const connect = () => {
        const socket = new SockJS('http://localhost:8080/ws');
        stompClientRef.current = new Client({
            webSocketFactory: () => socket,
            onConnect: onConnected,
            onStompError: onError
        });
        stompClientRef.current.activate();
    };

    const onConnected = () => {
        setUserData({...userData, "connected": true});
        stompClientRef.current.subscribe('/chatroom/public', onMessageReceived);
        stompClientRef.current.subscribe(`/user/${userData.username}/private`, onPrivateMessage);
        userJoin();
    };

    const userJoin = () => {
        const chatMessage = {
            senderName: userData.username,
            status: "JOIN"
        };
        console.log(chatMessage.senderName, "is connected")
        stompClientRef.current.publish({destination: "/app/message", body: JSON.stringify(chatMessage)});
    };

    const onMessageReceived = (payload) => {
        const payloadData = JSON.parse(payload.body);
        switch (payloadData.status) {
            case "JOIN":
                if (!privateChats.get(payloadData.senderName)) {
                    privateChats.set(payloadData.senderName, []);
                    setPrivateChats(new Map(privateChats));
                }
                break;
            case "MESSAGE":
                publicChats.push(payloadData);
                setPublicChats([...publicChats]);
                break;
        }
    };

    const onPrivateMessage = (payload) => {
        console.log(payload);
        const payloadData = JSON.parse(payload.body);
        if (privateChats.get(payloadData.senderName)) {
            privateChats.get(payloadData.senderName).push(payloadData);
            setPrivateChats(new Map(privateChats));
        } else {
            const list = [];
            list.push(payloadData);
            privateChats.set(payloadData.senderName, list);
            setPrivateChats(new Map(privateChats));
        }
    };

    const onError = (err) => {
        console.log(err);
    };

    const handleMessage = (event) => {
        const {value} = event.target;
        setUserData({...userData, "message": value});
    };

    const sendValue = () => {
        if (stompClientRef.current) {
            const chatMessage = {
                senderName: userData.username,
                message: userData.message,
                status: "MESSAGE"
            };
            stompClientRef.current.publish({destination: "/app/message", body: JSON.stringify(chatMessage)});
            setUserData({...userData, "message": ""});
        }
    };

    const sendPrivateValue = () => {
        if (stompClientRef.current) {
            const chatMessage = {
                senderName: userData.username,
                receiverName: tab,
                message: userData.message,
                status: "MESSAGE"
            };
            if (userData.username !== tab) {
                privateChats.get(tab).push(chatMessage);
                setPrivateChats(new Map(privateChats));
            }
            stompClientRef.current.publish({destination: "/app/private-message", body: JSON.stringify(chatMessage)});
            setUserData({...userData, "message": ""});
        }
    };

    const handleUsername = (event) => {
        const {value} = event.target;
        setUserData({...userData, "username": value});
    };

    const registerUser = () => {
        connect();
    };

    return (
        <Container>
            {userData.connected ? (
                <Box sx={{display: 'flex', flexDirection: 'column', height: '100vh'}}>
                    <Box sx={{display: 'flex'}}>
                        <Box sx={{width: '200px', borderRight: '1px solid #ccc'}}>
                            <List>
                                <ListItem button selected={tab === "CHATROOM"} onClick={() => setTab("CHATROOM")}>
                                    <ListItemText primary="Chatroom"/>
                                </ListItem>
                                {[...privateChats.keys()].map((name, index) => (
                                    <ListItem
                                        button
                                        selected={tab === name}
                                        onClick={() => setTab(name)}
                                        key={index}
                                    >
                                        <ListItemText primary={name}/>
                                    </ListItem>
                                ))}
                            </List>
                        </Box>
                        <Box sx={{flex: 1, display: 'flex', flexDirection: 'column'}}>
                            <Box sx={{flex: 1, overflowY: 'auto', borderBottom: '1px solid #ccc'}}>
                                <List>
                                    {(tab === "CHATROOM" ? publicChats : privateChats.get(tab) || []).map((chat, index) => (
                                        <ListItem key={index} sx={{
                                            margin: "10px",
                                            bgcolor: 'lightgrey',
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: chat.senderName === userData.username ? 'flex-end' : 'flex-start'
                                        }}>
                                                {chat.senderName !== userData.username &&
                                                    <ListItemText primary={chat.senderName}/>}
                                                <ListItemText secondary={chat.message} sx={{maxWidth: '60%'}}/>
                                                {chat.senderName === userData.username &&
                                                    <ListItemText primary={chat.senderName}/>}
                                        </ListItem>
                                    ))}
                                </List>
                            </Box>
                            <Box sx={{padding: 2}}>
                                <TextField
                                    label="Enter the message"
                                    value={userData.message}
                                    onChange={handleMessage}
                                    fullWidth
                                />
                                <Button variant="contained" onClick={tab === "CHATROOM" ? sendValue : sendPrivateValue}
                                        sx={{marginTop: 2}}>
                                    Send
                                </Button>
                            </Box>
                        </Box>
                    </Box>
                </Box>
            ) : (
                <Box sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100vh'
                }}>
                    <TextField
                        id="user-name"
                        label="Enter your name"
                        value={userData.username}
                        onChange={handleUsername}
                        fullWidth
                        sx={{marginBottom: 2}}
                    />
                    <Button variant="contained" onClick={registerUser}>
                        Connect
                    </Button>
                </Box>
            )}
        </Container>
    );
};

export default ChatRoom;
