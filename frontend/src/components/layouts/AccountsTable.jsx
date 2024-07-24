import React from "react";
import {
    Box,
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import AddIcon from '@mui/icons-material/Add';
import AccountService from "../../services/AccountService";

const AccountsTable = ({ accounts, firstName, setFirstName, lastName, myId,
                           setLastName, handleSearch, setLoading, setError, handleAddContact }) => {
    const renderHead = () => (
        <TableRow>
            <TableCell align="center"><strong>Account</strong></TableCell>
            <TableCell align="center"><strong>Email</strong></TableCell>
            <TableCell align="center"><strong>Add to contacts</strong></TableCell>
        </TableRow>
    );

    const add = async (id) => {
        setLoading(true);
        try {
            const response = await AccountService.addToContact(myId, id);
            console.log(response);
            handleAddContact(id);  // update the state in the parent component
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    }

    const renderBody = () => {
        return accounts.map((account) => (
            <TableRow key={account.id}>
                <TableCell align="center">{account.firstName} {account.lastName}</TableCell>
                <TableCell align="center">{account.email}</TableCell>
                <TableCell align="center">
                    <IconButton onClick={() => add(account.id)}>
                        <AddIcon />
                    </IconButton>
                </TableCell>
            </TableRow>
        ));
    };

    return (
        <>
            <Box style={{ display: 'flex', alignItems: 'center', marginBottom: '20px' }}>
                <TextField
                    label="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    variant="outlined"
                    style={{ marginRight: '10px' }}
                />
                <TextField
                    label="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    variant="outlined"
                    style={{ marginRight: '10px' }}
                />
                <Button onClick={handleSearch} variant="contained" color="primary">Search</Button>
            </Box>
            <TableContainer component={Paper}>
                <Table aria-label="custom pagination table">
                    <TableHead>{renderHead()}</TableHead>
                    <TableBody>{renderBody()}</TableBody>
                </Table>
            </TableContainer>
        </>
    );
};

export default AccountsTable;
