import React from 'react';
import { useTranslation } from 'react-i18next';
import { HashRouter, Route, Switch } from 'react-router-dom';
import LocaleContext from '../../contexts/LocaleContext';
import PageInfoDispatchContext from '../../contexts/PageInfoDispatchContext';
import { LOCALE } from '../../lib/Constraints';
import { usePageInfoReducer } from '../../reducers/PageInfoReducer';
import Main from '../Main/Main';
import QuestionAdd from '../QuestionAdd/QuestionAdd';
import QuestionEdit from '../QuestionEdit/QuestionEdit';
import SignIn from '../SignIn/SignIn';
import SignUp from '../SignUp/SignUp';
import './App.css';
import AppNavbar from './AppNavbar';

function App() {
    const [pageInfoState, pageInfoDispatch] = usePageInfoReducer();
    const [locale, setLocale] = React.useState(LOCALE.EN);
    const { t, i18n } = useTranslation();

    const changeLangCallback = React.useCallback((locale) => {
        i18n.changeLanguage(locale.short);
        setLocale(locale);
    }, [i18n]);

    return (
        <HashRouter>
            <div className="App">

                <AppNavbar 
                    currentLang={i18n.languages[0]}
                    changeLangCallback={changeLangCallback}
                />

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
