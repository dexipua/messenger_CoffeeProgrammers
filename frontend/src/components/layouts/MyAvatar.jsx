import * as React from 'react';
import Avatar from '@mui/material/Avatar';

function stringToColor(string) {
    let hash = 0;
    for (let i = 0; i < string.length; i += 1) {
        hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = '#';
    for (let i = 0; i < 3; i += 1) {
        const value = (hash >> (i * 8)) & 0xff;
        color += `00${value.toString(16)}`.slice(-2);
    }
    return color;
}

function stringAvatar(name) {
    const nameParts = name.split(' ');
    const firstLetter = nameParts[0] ? nameParts[0][0] : '';
    const secondLetter = nameParts[1] ? nameParts[1][0] : firstLetter;

    return {
        sx: {
            bgcolor: stringToColor(name),
            width: 34,
            height: 34

        },
        children: `${firstLetter}${secondLetter}`,
    };
}

export default function MyAvatar({ name }) {
    return  <Avatar {...stringAvatar(name)} />;
}
