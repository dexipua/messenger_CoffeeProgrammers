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

const AccountsTable = ({ accounts, firstName, setFirstName, lastName, setLastName, handleSearch }) => {
    const renderHead = () => (
        <TableRow>
            <TableCell align="center"><strong>Account</strong></TableCell>
            <TableCell align="center"><strong>Email</strong></TableCell>
        </TableRow>
    );

    const renderBody = () => {
        return accounts.map((account) => (
            <TableRow key={account.id}>
                <TableCell align="center">{account.firstName} {account.lastName}</TableCell>
                <TableCell align="center">{account.email}</TableCell>
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