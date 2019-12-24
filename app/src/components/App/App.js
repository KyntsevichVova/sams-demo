import React from 'react';
import { useTranslation } from 'react-i18next';
import { HashRouter, Route, Switch } from 'react-router-dom';
import LocaleContext from '../../contexts/LocaleContext';
import PageInfoDispatchContext from '../../contexts/PageInfoDispatchContext';
import flag_russia from '../../img/flag_russia.png';
import flag_uk from '../../img/flag_uk.png';
import { LOCALE } from '../../lib/Constraints';
import { usePageInfoReducer } from '../../reducers/PageInfoReducer';
import Main from '../Main/Main';
import QuestionAdd from '../QuestionAdd/QuestionAdd';
import QuestionEdit from '../QuestionEdit/QuestionEdit';
import SignIn from '../SignIn/SignIn';
import SignUp from '../SignUp/SignUp';
import './App.css';

function App() {
    const [pageInfoState, pageInfoDispatch] = usePageInfoReducer();
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
                    <PageInfoDispatchContext.Provider value={pageInfoDispatch}>
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
                    </PageInfoDispatchContext.Provider>
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
