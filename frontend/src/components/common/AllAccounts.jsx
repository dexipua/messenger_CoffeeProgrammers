import React, {useEffect, useState} from "react";
import {
    Box,
    Button,
    IconButton,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    TextField,
    Typography
} from "@mui/material";
import AccountService from "../../services/AccountService";
import PaginationComponent from "../layouts/PaginationComponent";
import MyAvatar from "../layouts/MyAvatar";
import Cookies from "js-cookie";
import {Add} from "@mui/icons-material";

const styles = {
    formContainer: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center'
    },
    button: {
        margin: 8  // Changed from `margin: 1` to `margin: 8` for better spacing
    },
    listItem: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
    },
    listItemButton: {
        display: 'flex',
        alignItems: 'center'
    },
    listItemText: {
        marginLeft: 8
    }
};

const AllAccounts = () => {
    const [filteredAccounts, setFilteredAccounts] = useState([]);

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    const [isSearching, setIsSearching] = useState(false);


    const myId = Cookies.get('id');
    const size = 3;
    const [update, setUpdate] = useState(false);

    useEffect(() => {
        setUpdate(false);
        const getData = async () => {
            try {
                const response = await AccountService.getAllAccountWithoutContacts(myId, page, size);
                console.log(response);
                setFilteredAccounts(response.list);
                setTotalPages(response.pages);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        if (isSearching) {
            handleSearch();
        } else {
            getData();
        }
    }, [page, isSearching, update]);

    const handleSearch = async () => {
        setLoading(true);
        try {
            const response = await AccountService.getAllAccountWithoutContactsWithSearch(
                myId, page, size, firstName, lastName
            );
            console.log(response);
            setFilteredAccounts(response.list);
            setTotalPages(response.pages);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const handleAddContact = async (id) => {
        await AccountService.addToContact(myId, id)
        setIsSearching(false);
        setUpdate(true);
    };

    const handleChange = (event, value) => {
        setPage(value - 1);
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    return (
        <Box width={"100%"}>
            <Box
                component="form"
                onSubmit={(e) => {
                    e.preventDefault();
                    handleSearch();
                }}
                style={styles.formContainer}
            >
                <TextField
                    label="First Name"
                    type="text"
                    variant={"standard"}
                    value={firstName}
                    size="small"
                    onChange={(e) => setFirstName(e.target.value)}
                />
                <TextField
                    label="Last Name"
                    type="text"
                    variant={"standard"}
                    size="small"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                />
                <Button style={styles.button} type="submit" variant="contained" color="primary">
                    Search
                </Button>
            </Box>
            {filteredAccounts.map((account) => (
                <ListItem key={account.id} disablePadding>
                    <ListItemButton>
                        <Box mr={-2.5}>
                            <ListItemIcon>
                                <MyAvatar name={`${account.firstName} ${account.lastName}`}/>
                            </ListItemIcon>
                        </Box>
                        <ListItemText style={styles.listItemText}>
                            <Typography variant="body2" mb={-1} noWrap>
                                {`${account.firstName} ${account.lastName}`}
                            </Typography>
                            <Typography variant="caption">
                                {account.email}
                            </Typography>
                        </ListItemText>
                    </ListItemButton>
                    <IconButton size="small" edge="end" onClick={() => handleAddContact(account.id)}>
                        <Add/>
                    </IconButton>
                </ListItem>
            ))}
            {totalPages > 1 &&
                <PaginationComponent
                    currentPage={page + 1}
                    totalPages={totalPages}
                    onPageChange={handleChange}
                />
            }
        </Box>
    );
};

export default AllAccounts;
