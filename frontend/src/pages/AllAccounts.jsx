import React, {useEffect, useState} from "react";
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
    TextField,
} from "@mui/material";
import AccountService from "../services/AccountService";
import PaginationComponent from "../components/layouts/PaginationComponent";

const AllAccounts = () => {
    const [page, setPage] = React.useState(0);
    const rowsPerPage = useState(2);
    const [accounts, setAccounts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const [maxPage, setMaxPage] = useState(0);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    const loadAccounts = async () => {
        setLoading(true);
        try {
            const response = (firstName || lastName)
                ? await AccountService.getAllByName({firstName, lastName, page, size: rowsPerPage})
                : await AccountService.getAll(page, rowsPerPage);
            console.log('GetAll successful:', response);
            setAccounts(response.list);
            setMaxPage(response.pages);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadAccounts();
    }, [page, rowsPerPage]); // Add page and rowsPerPage to the dependency array

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

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

    const handleChange = (event, value) => {
        setPage(value - 1);
    };

    const handleSearch = () => {
        setPage(0);
        loadAccounts();
    };

    return (
        <Box width={"100%"} style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <Box style={{display: 'flex', alignItems: 'center', marginBottom: '20px'}}>
                <TextField
                    label="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    variant="outlined"
                    style={{marginRight: '10px'}}
                />
                <TextField
                    label="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    variant="outlined"
                    style={{marginRight: '10px'}}
                />
                <Button onClick={handleSearch} variant="contained" color="primary">Search</Button>
            </Box>
            <TableContainer component={Paper}>
                <Table aria-label="custom pagination table">
                    <TableHead>{renderHead()}</TableHead>
                    <TableBody>{renderBody()}</TableBody>
                </Table>
            </TableContainer>
            <PaginationComponent
                currentPage={page + 1}
                totalPages={maxPage + 1}
                onPageChange={handleChange}
            />
        </Box>
    );
};

export default AllAccounts;
