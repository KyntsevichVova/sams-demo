import React from 'react';
import Main from '../Main/Main';
import './App.css';
import { HashRouter, Switch, Route } from 'react-router-dom';
import QuestionAdd from '../QuestionAdd/QuestionAdd';
import QuestionEdit from '../QuestionEdit/QuestionEdit';
import { PAGE_SIZES, FILTERS } from '../../lib/Constraints';
import PageDispatch from '../../contexts/PageDispatch';
import flag_russia from '../../flag_russia.png';
import flag_uk from '../../flag_uk.png';
import '../../lib/i18n';
import { useTranslation } from 'react-i18next';

const initialState = {
    pageNumber: 0, 
    pageSize: PAGE_SIZES[0],
    filter: FILTERS[0].filter
};

function pageReducer(state, action) {
    switch (action.type) {
        case 'pageNumber':
            return { ...state, pageNumber: action.pageNumber };
        case 'pageSize':
            return { ...state, pageSize: action.pageSize };
        case 'filter':
            return { ...state, filter: action.filter };
        default:
            throw new Error();
    }
}

function App() {
    const [state, dispatch] = React.useReducer(pageReducer, initialState);
    const { t, i18n } = useTranslation();
    const changeLang = (lng) => {
        i18n.changeLanguage(lng);
    };

    return (
        <HashRouter>
            <div className="App">

                <nav className="navbar navbar-dark mb-5 shadow-sm bg-primary border-bottom">
                    <span className="navbar-brand" style={{fontSize: '2.5rem'}}>{t('main.title')}</span>
                    <div className="d-flex flex-row justify-content-end">
                        <img 
                            src={ flag_russia } 
                            className={`cursor-pointer${i18n.languages[0] !== 'ru' ? " opaque-5" : ""}`}
                            alt="ru" 
                            onClick={()=>{changeLang('ru')}}
                            height="48"
                            width="48"
                        />
                        <img 
                            src={ flag_uk } 
                            className={`cursor-pointer${i18n.languages[0] !== 'en' ? " opaque-5" : ""}`}
                            alt="en" 
                            onClick={()=>{changeLang('en')}}
                            height="48"
                            width="48"
                        />
                    </div>
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
                                    pageSize={state.pageSize}
                                    filter={state.filter}
                                />
                            </Route>
                        </Switch>
                    </PageDispatch.Provider>
                </div>

                <footer className="footer mt-5">
                    <div className="bg-light d-flex flex-column align-items-center justify-content-center w-100">
                        <span>© {t('main.author')} </span>
                        <span>vkyntsevich@gmail.com</span>
                    </div>
                </footer>

            </div>
        </HashRouter>
    );
}

export default App;
