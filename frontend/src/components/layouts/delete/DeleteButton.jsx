import * as React from 'react';
import DeleteIcon from "./DeleteIcon";
import ConfirmDialog from "../ConfirmDialog";


export default function DeleteButton({text, deleteFunction}) {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <DeleteIcon
                someFunction={handleClickOpen}
            />
            <ConfirmDialog
                onClose={handleClose}
                open={open}
                text={text}
                someFunction={deleteFunction}
            />
        </>
    );
}