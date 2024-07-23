import * as React from "react";
import Box from "@mui/material/Box";
import {styled} from "@mui/material/styles";
import {Container} from "@mui/material";
import Chat from "../common/Chat";

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
}));

const ContentSection = ({selectedChatId}) => {
    return (
        <Container>
            <Box component="main" sx={{flexGrow: 1, p: 3}}>
                <DrawerHeader/>
                <Box mb='50px'>
                    {selectedChatId && <Chat selectedChatId={selectedChatId}/>}
                </Box>
            </Box>
        </Container>
    )
}

export default ContentSection;