import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App/App';
import './index.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faExternalLinkAlt, faPlus, faSearch } from '@fortawesome/free-solid-svg-icons';
import { faEdit, faTrashAlt } from '@fortawesome/free-regular-svg-icons';

library.add(faExternalLinkAlt, faEdit, faTrashAlt, faPlus, faSearch);

ReactDOM.render(<React.Suspense fallback={<span>Loading</span>}><App /></React.Suspense>, document.getElementById('root'));