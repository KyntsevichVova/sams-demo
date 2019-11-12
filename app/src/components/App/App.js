import React from 'react';
import Main from '../Main/Main';
import './App.css';
import { BrowserRouter, Switch, Route } from 'react-router-dom';

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <nav className="navbar navbar-dark mb-5 shadow-sm bg-primary border-bottom">
                    <span className="navbar-brand" style={{fontSize: '2.5rem'}}>Java Interview Notes</span>
                </nav>
                <Switch>
                    <Route path="/">
                        <Main/>
                    </Route>
                </Switch>
                <footer className="footer mt-5">
                    <div className="bg-light d-flex flex-column align-items-center justify-content-center w-100">
                        <span>© Vladimir Kuntsevich</span>
                        <span>vkyntsevich@gmail.com</span>
                    </div>
                </footer>
            </div>
        </BrowserRouter>
    );
}

export default App;
