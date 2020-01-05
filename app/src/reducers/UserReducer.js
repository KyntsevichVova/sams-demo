import React from 'react';
import { STORAGE_JWT } from '../lib/Constraints';

const userInitialState = {
    loggedIn: false,
    username: "",
    roles: []
};

function userReducer(state, action) {
    switch (action.type) {
        case 'signin':
            sessionStorage.setItem(STORAGE_JWT, action.token);
            let [headers, payload, sign] = action.token.substr(7).split('.').map((value, index) => index < 2 ? JSON.parse(atob(value)) : value);
            let username = JSON.parse(atob(payload.metadata)).username;
            let roles = payload.roles;
            return { loggedIn: true, username: username, roles: roles };
        case 'signout':
            sessionStorage.removeItem(STORAGE_JWT);
            return userInitialState;
        default:
            throw new Error();
    }
}

export function useUserReducer() {
    return React.useReducer(userReducer, userInitialState);
}