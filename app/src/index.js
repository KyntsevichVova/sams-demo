import React from 'react';
import ReactDOM from 'react-dom';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEdit, faTrashAlt } from '@fortawesome/free-regular-svg-icons';
import { faExclamationTriangle, faExternalLinkAlt, faPlus, faSearch } from '@fortawesome/free-solid-svg-icons';
import App from './components/App/App';
import './index.css';
import './lib/i18n';
import JWT from './lib/JWT';

library.add(faExternalLinkAlt, faEdit, faTrashAlt, faPlus, faSearch, faExclamationTriangle);

setInterval(() => {
    if (JWT.tokenExpired()) {
        JWT.clearStorage();
    }
}, 30 * 60 * 1000);

let token = JWT.getStorage();

if (token) {
    JWT.updateToken(token);
}

if (JWT.tokenExpired()) {
    JWT.clearStorage();
}

ReactDOM.render(
    (
        <React.Suspense fallback={<span>Loading</span>}>
            <App />
        </React.Suspense>
    ), document.getElementById('root')
);