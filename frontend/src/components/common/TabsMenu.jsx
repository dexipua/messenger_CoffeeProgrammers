import * as React from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Typography from "@mui/material/Typography";
import List from "@mui/material/List";

const tabs = [
    {
        key: "MY_ACCOUNT",
        label: "My account",
    },
    {
        key: "CHATS",
        label: "Chats",
    },
    {
        key: "CONTACTS",
        label: "Contacts",
    },
    {
        key: "ALL_USERS",
        label: "All users",
    },

];


const TabsMenu = ({selectTab}) => {
    return (
        <List>
            {tabs.map((tab) => (
                <ListItem key={tab.label} disablePadding>
                    <ListItemButton onClick={() => selectTab(tab.key)}>
                        <ListItemText >
                            <Typography variant="body2">
                                {tab.label}
                            </Typography>
                        </ListItemText>
                    </ListItemButton>
                </ListItem>
            ))}
        </List>
    )
}

export default TabsMenu;