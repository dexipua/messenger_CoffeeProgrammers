import React, {useEffect, useState} from "react";
import {Box} from "@mui/material";
import AccountService from "../services/AccountService";
import PaginationComponent from "../components/layouts/PaginationComponent";
import AccountsTable from "../components/layouts/AccountsTable";

const AllAccounts = () => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(2);
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
                ? await AccountService.getAllByName({ firstName, lastName, page, size: rowsPerPage })
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
    }, [page, rowsPerPage]);

    if (error) {
        return <p>Error: {error}</p>;
    }

    const handleChange = (event, value) => {
        setPage(value - 1);
    };

    const handleSearch = () => {
        setPage(0);
        loadAccounts();
    };

    return (
        <Box width={"100%"} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <AccountsTable
                accounts={accounts}
                firstName={firstName}
                setFirstName={setFirstName}
                lastName={lastName}
                setLastName={setLastName}
                handleSearch={handleSearch}
            />
            <PaginationComponent
                currentPage={page + 1}
                totalPages={maxPage + 1}
                onPageChange={handleChange}
            />
        </Box>
    );
};

export default AllAccounts;