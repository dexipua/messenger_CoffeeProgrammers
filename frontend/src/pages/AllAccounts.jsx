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
    const myId = Cookies.get('id');
    const [isSearching, setIsSearching] = useState(false);
    const size = 1;
    const [update, setUpdate] = useState(false)

    useEffect(() => {
        setUpdate(false)
        const getData = async () => {
            try {
                const response = await AccountService.getAllAccountWithoutContacts(myId, page, size);
                console.log(response);
                setAccounts(response.list);
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
        if (!isSearching) {
            setPage(0);
        }
        setLoading(true);
        try {
            const response = await AccountService.getAllAccountWithoutContactsWithSearch(
                myId, page, size, firstName, lastName);
            console.log(response);
            setIsSearching(true);
            setFilteredAccounts(response.list);
            setTotalPages(response.pages);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const handleAddContact = (id) => {
        setIsSearching(false)
        setPage(0)
        setUpdate(true)
    };

    const handleChange = (event, value) => {
        setPage(value - 1);
    };

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
                setLoading={setLoading}
                setError={setError}
                myId={myId}
                handleAddContact={handleAddContact}
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
