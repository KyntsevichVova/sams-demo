import React from 'react';
import Main from '../Main/Main';
import './App.css';
import { HashRouter, Switch, Route } from 'react-router-dom';
import QuestionAdd from '../QuestionAdd/QuestionAdd';
import QuestionEdit from '../QuestionEdit/QuestionEdit';
import { PAGE_SIZES, FILTERS, LOCALE } from '../../lib/Constraints';
import PageDispatch from '../../contexts/PageInfoDispatchContext';
import LocaleContext from '../../contexts/LocaleContext';
import flag_russia from '../../flag_russia.png';
import flag_uk from '../../flag_uk.png';
import '../../lib/i18n';
import { useTranslation } from 'react-i18next';
import SignIn from '../SignIn/SignIn';
import SignUp from '../SignUp/SignUp';

const pageInfoInitialState = {
    pageNumber: 0, 
    pageSize: PAGE_SIZES[0],
    filter: FILTERS[0].filter
};

function pageInfoReducer(state, action) {
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
    const [pageInfoState, pageInfoDispatch] = React.useReducer(pageInfoReducer, pageInfoInitialState);
    const [locale, setLocale] = React.useState(LOCALE.EN);
    const { t, i18n } = useTranslation();
    const changeLang = (locale) => {
        i18n.changeLanguage(locale.short);
        setLocale(locale);
    };

    return (
        <HashRouter>
            <div className="App">

                <nav className="navbar navbar-dark mb-5 shadow-sm bg-primary border-bottom">
                    <span className="navbar-brand" style={{fontSize: '2.5rem'}}>{t('main.title')}</span>
                    <div className="d-flex flex-row justify-content-end">
                        <img 
                            src={ flag_russia } 
                            className={`cursor-pointer${i18n.languages[0] !== LOCALE.RU.short ? " opaque-5" : ""}`}
                            alt={ LOCALE.RU.full } 
                            onClick={ ()=>{changeLang(LOCALE.RU)} }
                            height="48"
                            width="48"
                        />
                        <img 
                            src={ flag_uk } 
                            className={`cursor-pointer${i18n.languages[0] !== LOCALE.EN.short ? " opaque-5" : ""}`}
                            alt={ LOCALE.EN.full } 
                            onClick={ ()=>{changeLang(LOCALE.EN)} }
                            height="48"
                            width="48"
                        />
                    </div>
                </nav>

                <div className="content">
                    <PageDispatch.Provider value={pageInfoDispatch}>
                        <LocaleContext.Provider value={locale}>
                            <Switch>
                                <Route path="/add">
                                    <QuestionAdd />
                                </Route>

                                <Route 
                                    path="/edit/:questionId" 
                                    component={QuestionEdit}
                                />

                                <Route 
                                    path="/signin"
                                    component={SignIn}
                                />

                                <Route
                                    path="/signup"
                                    component={SignUp}
                                />

                                <Route exact path="/">
                                    <Main 
                                        pageNumber={pageInfoState.pageNumber}
                                        pageSize={pageInfoState.pageSize}
                                        filter={pageInfoState.filter}
                                    />
                                </Route>
                            </Switch>
                        </LocaleContext.Provider>
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
