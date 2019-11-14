import React from 'react';
import Main from '../Main/Main';
import './App.css';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import QuestionAdd from '../QuestionAdd/QuestionAdd';
import QuestionEdit from '../QuestionEdit/QuestionEdit';
import { BASE_SUBDIR } from '../../Constraints';
import PageDispatch from '../../contexts/PageDispatch';

const initialState = { pageNumber: 0 };

function pageReducer(state, action) {
    return { pageNumber: action.pageNumber }
}

function App() {
    const [state, dispatch] = React.useReducer(pageReducer, initialState);

    return (
        <BrowserRouter basename={`/${BASE_SUBDIR}`}>
            <div className="App">

                <nav className="navbar navbar-dark mb-5 shadow-sm bg-primary border-bottom">
                    <span className="navbar-brand" style={{fontSize: '2.5rem'}}>Java Interview Notes</span>
                </nav>

                <div className="content">
                    <PageDispatch.Provider value={dispatch}>
                        <Switch>
                            <Route path="/add">
                                <QuestionAdd />
                            </Route>

                            <Route 
                                path="/edit/:questionId" 
                                component={QuestionEdit}
                            />

                            <Route exact path="/">
                                <Main 
                                    pageNumber={state.pageNumber}
                                />
                            </Route>
                        </Switch>
                    </PageDispatch.Provider>
                </div>

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
