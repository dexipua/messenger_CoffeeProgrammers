import {Pagination} from '@mui/material';

const PaginationComponent = ({ currentPage, totalPages, onPageChange }) => {
    return (
        <Pagination
            count={totalPages}
            page={currentPage}
            onChange={onPageChange}
            showFirstButton
            showLastButton
            size="large"
            sx={{ margin: 2, justifyContent: 'center', display: 'flex' }}
        />
    );
};

export default PaginationComponent;