import React from 'react';

const userInitialState = {
    loggedIn: false,
    username: ""
};

function userReducer(state, action) {
    switch (action.type) {
        case 'signin':
            return { loggedIn: true, username: action.username };
        case 'signup':
            return { loggedIn: true, username: action.username };
        case 'signout':
            return userInitialState;
        default:
            throw new Error();
    }
}

export function useUserReducer() {
    return React.useReducer(userReducer, userInitialState);
}