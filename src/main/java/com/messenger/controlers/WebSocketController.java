package com.messenger.controlers;

import com.messenger.mapper.MessageMapper;
import com.messenger.services.interfaces.MessageService;
import com.messenger.testWebSocket.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        messagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }

    //TODO history for chats and messages
//    // Метод для отримання історії повідомлень
//    @MessageMapping("/history")
//    public void getHistory(Long chatId, Principal principal) {
//        // Знайти користувача за email
//        User user = userRepository.findByEmail(principal.getName());
//        // Знайти всі повідомлення для даного чату
//        List<Message> messages = messageRepository.findByChatId(chatId);
//        // Надіслати історію повідомлень користувачу
//        messagingTemplate.convertAndSendToUser(
//                user.getUserId().toString(),
//                "/queue/history",
//                messages
//        );
//    }
    //    @MessageMapping("/private-message/{chat_id}/myId/{my_account_id}")
//    public MessageResponse sendPrivateMessage(MessageRequest messageRequest,
//                                              @PathVariable("chat_id") long chatId,
//                                              @PathVariable("my_account_id") long accountId)
//            throws Exception {
//        Message savedMessage = messageService.create(chatId, accountId,
//                messageMapper.toModel(messageRequest));
//        Chat chat = savedMessage.getChat();
//        for (Account account : chat.getAccounts()) {
//            if (!account.getId().equals(savedMessage.getAccount().getId())) {
//                messagingTemplate.convertAndSendToUser(
//                        account.getId().toString(),
//                        "/private",
//                        savedMessage
//                );
//            }
//        }
//        System.out.println(messageMapper.toResponse(savedMessage));
//        return messageMapper.toResponse(savedMessage);
//    }
}
