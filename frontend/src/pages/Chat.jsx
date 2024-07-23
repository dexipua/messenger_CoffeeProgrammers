import React, {useEffect, useState} from 'react';
import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import Cookies from "js-cookie";
import SendMessageBar from "../components/common/SendMessageBar";
import axios from 'axios';

let stompClient = null;

const Chat = ({ selectedChatId }) => {
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const setupWebSocket = async () => {
            try {
                const socket = new SockJS('http://localhost:8080/ws');
                stompClient = new Client({
                    webSocketFactory: () => socket,
                    debug: (str) => console.log(str),
                    onConnect: () => {
                        stompClient.subscribe(`/user/${Cookies.get('id')}/queue/messages`, onMessageReceived);
                    },
                    onStompError: (frame) => {
                        console.error('Broker reported error: ' + frame.headers['message']);
                        console.error('Additional details: ' + frame.body);
                    }
                });

                if (selectedChatId) {
                    const token = Cookies.get('token');
                    const response = await axios.get(
                        `http://localhost:8080/messages/getByChat/${selectedChatId}`,
                        {
                            withCredentials: true,
                            headers: {
                                'Authorization': `Bearer ${token}`
                            }
                        }
                    );

                    setMessages(response.data);

                }

                stompClient.activate();
            } catch (error) {
                console.error('WebSocket connection or data fetch error:', error);
            } finally {
                setLoading(false); // Завершити завантаження
            }
        };

        setupWebSocket();

        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, [selectedChatId]);

    const sendMessage = () => {
        if (stompClient && stompClient.connected) {
            const chatMessage = {
                senderId: Cookies.get('id'),
                text: message,
                chatId: selectedChatId,
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
            {loading ? (
                <div>Loading...</div>
            ) : (
                <>
                    <div>
                        {messages && messages.map((msg) => (
                            <div key={msg.id}>
                                <b>{msg.sender.firstName + " " + msg.sender.lastName}</b>: {msg.text}
                            </div>
                        ))}
                    </div>
                    <SendMessageBar
                        message={message}
                        changeMessage={setMessage}
                        sendMessage={sendMessage}
                    />
                </>
            )}
        </div>
    );
};

export default Chat;
