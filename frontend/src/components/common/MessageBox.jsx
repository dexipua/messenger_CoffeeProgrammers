import {Box, Card, CardContent, CardHeader} from "@mui/material";
import MyAvatar from "../layouts/MyAvatar";
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
    {
        console.log(myId, senderId)
    }

    return (
        <Box m={2}>
            <Card elevation={2}>
                {myId + "" === senderId + "" ? (
                    <RightAlignedCardHeader
                        avatar={
                            <Box ml={2}>
                                <MyAvatar name={fullName}/>
                            </Box>
                        }
                        title={
                            <Typography variant="body2">
                                <b>{fullName}</b>
                            </Typography>
                        }
                        subheader={date}
                    />
                ) : (
                    <CardHeader
                        avatar={<MyAvatar name={fullName}/>}
                        title={
                            <Typography variant='body2'>
                                {fullName}
                            </Typography>
                        }
                        subheader={date}
                    />
                )}
                <CardContent>
                    <Typography variant="body2" color="text.secondary">
                        {text}
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    )
}

export default MessageBox;

