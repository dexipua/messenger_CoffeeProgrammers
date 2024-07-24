import {Pagination} from '@mui/material';

const PaginationComponent = ({ currentPage, totalPages, onPageChange }) => {
    return (
        <Pagination
            count={totalPages}
            page={currentPage}
            siblingCount={0}
            onChange={onPageChange}
            size="small"
            sx={{ margin: 2, justifyContent: 'center', display: 'flex' }}
        />
    );
};

export default PaginationComponent;