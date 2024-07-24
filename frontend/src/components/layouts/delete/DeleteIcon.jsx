import React from 'react';
import IconButton from '@mui/material/IconButton';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';

const DeleteIcon = ({someFunction}) => {

    return (
        <IconButton
            onClick={() => someFunction()}
            onMouseEnter={(e) => e.currentTarget.style.color = 'red'}
            onMouseLeave={(e) => e.currentTarget.style.color = ''}
            aria-label="delete"
            sx={{ minWidth: 'unset', width: 'auto', p: 0, fontSize: 24  }}
        >
            <DeleteForeverIcon />
        </IconButton>
    );
};

export default DeleteIcon;