import React, {useEffect, useState} from "react";
import {Box} from "@mui/material";
import AccountService from "../services/AccountService";
import PaginationComponent from "../components/layouts/PaginationComponent";
import AccountsTable from "../components/layouts/AccountsTable";
import Cookies from "js-cookie";

const AllAccounts = () => {
    const [accounts, setAccounts] = useState([]);
    const [filteredAccounts, setFilteredAccounts] = useState([]);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const myId = Cookies.get('id')

    useEffect(() => {
        const getData = async () => {
            try {
                const response = await AccountService.getAllAccountWithoutContacts(myId, page, 2);
                console.log(response)
                setAccounts(response.list);
                setFilteredAccounts(response.list);
                setTotalPages(response.pages);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        getData();
    }, [page]);

    const handleSearch = async () => {
        setPage(0);
        setLoading(true);
        // if (firstName || lastName) {
        //     const response =
        //     setFilteredUsers(response);
        //     setFirstName('');
        //     setLastName('')
        // } else {
        //     setFilteredUsers(users);
        // }
        try {
            const response = await AccountService.getAllByName(firstName, lastName, 0, 2);
            setFilteredAccounts(response.list);
            setTotalPages(response.pages);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (event, value) => {
        setPage(value - 1);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    return (
        <Box width={"100%"} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <AccountsTable
                accounts={filteredAccounts}
                firstName={firstName}
                setFirstName={setFirstName}
                lastName={lastName}
                setLastName={setLastName}
                handleSearch={handleSearch}
            />
            <PaginationComponent
                currentPage={page + 1}
                totalPages={totalPages + 1}
                onPageChange={handleChange}
            />
        </Box>
    );
};

export default AllAccounts;
