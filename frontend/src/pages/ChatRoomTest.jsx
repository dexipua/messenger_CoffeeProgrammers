import React, {useEffect, useState} from 'react';
import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import Cookies from "js-cookie";

let stompClient = null;

const PrivateChat = () => {
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");
    const [chatId, setChatId] = useState(""); // ID чату

    const account = Cookies.get('Account')
    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        stompClient = new Client({
            webSocketFactory: () => socket,
            debug: (str) => console.log(str),
            onConnect: () => {
                stompClient.subscribe('/user/queue/messages', onMessageReceived);
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            }
        });

        stompClient.activate();

        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, []);

    const sendMessage = () => {
        if (stompClient && stompClient.connected) {
            console.log(account.lastName)
            const chatMessage = {
                sender: account.firstName + " " + account.lastName,
                content: message,
                chatId: chatId,
                accountId: account.id
            };

            stompClient.publish({
                destination: '/app/private-message',
                body: JSON.stringify(chatMessage),
                headers: {
                    'content-type': 'application/json'
                }
            });

            setMessage("");
        }
    };

    const onMessageReceived = (payload) => {
        const receivedMessage = JSON.parse(payload.body);
        setMessages((prevMessages) => [...prevMessages, receivedMessage]);
    };

    return (
        <div>
            <h2>Private Chat</h2>
            <div>
                <input
                    type="text"
                    placeholder="Chat ID"
                    value={chatId}
                    onChange={(e) => setChatId(e.target.value)}
                />

            </div>
            <div>
                <input
                    type="text"
                    placeholder="Type a message..."
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                />
                <button onClick={sendMessage}>Send</button>
            </div>
            <div>
                {messages.map((msg, index) => (
                    <div key={index}>
                        <b>{msg.sender}</b>: {msg.content}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default PrivateChat;
