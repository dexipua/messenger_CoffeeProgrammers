import {Box, Card, CardContent, CardHeader} from "@mui/material";
import MyAvatar from "../../layouts/MyAvatar";
import Cookies from "js-cookie";
import Typography from "@mui/material/Typography";
import {styled} from "@mui/material/styles";

const RightAlignedCardHeader = styled(CardHeader)(({theme}) => ({
    display: "flex",
    flexDirection: "row-reverse",
    textAlign: "right",
    "& .MuiCardHeader-avatar": {
        marginRight: theme.spacing(2),
        marginLeft: 0,
    },
    "& .MuiCardHeader-content": {
        flex: "inherit",
    },
}));

const cardStyles = (isMyMessage) => ({
    bgcolor: isMyMessage ? "#ededed" : "white"
});

const MessageBox = (
    {
        message: {
            id: messageId,
            text,
            sender: {
                id: senderId,
                firstName,
                lastName,
                email
            },
            date
        }
    }
) => {

    const fullName = firstName + " " + lastName
    const myId = Cookies.get("id")

    const isMyMessage = myId + "" === senderId + ""

    return (
        <Box m={2} /*TODO*/>
            <Card elevation={2} sx={cardStyles(isMyMessage)}
            >
                {isMyMessage ? (
                    <RightAlignedCardHeader

                        avatar={
                            <Box ml={2}>
                                <MyAvatar name={fullName}/>
                            </Box>
                        }
                        title={
                            <Box mb={-0.5}>
                                <Typography variant="body2" fontWeight="bold">
                                    {fullName}
                                </Typography>
                            </Box>

                        }
                        subheader={
                            <Typography variant="caption" color='grey'>
                                {date}
                            </Typography>
                        }
                    />
                ) : (
                    <CardHeader
                        avatar={<MyAvatar name={fullName}/>}
                        title={
                            <Box mb={-0.5}>
                                <Typography variant="body2" fontWeight="bold">
                                    {fullName}
                                </Typography>
                            </Box>
                        }
                        subheader={
                            <Typography variant="caption" color='text.secondary'>
                                {date}
                            </Typography>
                        }
                    />
                )}
                <CardContent>
                    <Typography variant="body2">
                        {text}
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    )
}

export default MessageBox;

