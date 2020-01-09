import React from 'react';
import { useTranslation } from 'react-i18next';
import { HashRouter, Route, Switch } from 'react-router-dom';
import LocaleContext from '../../contexts/LocaleContext';
import PageInfoDispatchContext from '../../contexts/PageInfoDispatchContext';
import UserContext from '../../contexts/UserContext';
import { LOCALE } from '../../lib/Constraints';
import JWT from '../../lib/JWT';
import { usePageInfoReducer } from '../../reducers/PageInfoReducer';
import { useUserReducer } from '../../reducers/UserReducer';
import Main from '../Main/Main';
import QuestionAdd from '../QuestionAdd/QuestionAdd';
import QuestionEdit from '../QuestionEdit/QuestionEdit';
import SignIn from '../SignIn/SignIn';
import SignUp from '../SignUp/SignUp';
import './App.css';
import AppNavbar from './AppNavbar';

function App() {
    const [pageInfoState, pageInfoDispatch] = usePageInfoReducer();
    const [userState, userDispatch] = useUserReducer();

    const [locale, setLocale] = React.useState(LOCALE.EN);
    const { t, i18n } = useTranslation();

    const changeLangCallback = React.useCallback((locale) => {
        i18n.changeLanguage(locale.short);
        setLocale(locale);
    }, [i18n]);

    React.useEffect(() => {
        let token = JWT.getStorage();

        if (token) {
            userDispatch({ type: 'signin', token: token })
        }

        if (JWT.tokenExpired()) {
            userDispatch({ type: 'signout' });
        }

        let interval = setInterval(() => {
            if (JWT.tokenExpired()) {
                userDispatch({ type: 'signout' });
            }
        }, 30 * 60 * 1000);
        
        return () => {
            clearInterval(interval);
        }
    }, [userDispatch]);

    return (
        <HashRouter>
            <UserContext.Provider value={{userState: userState, userDispatch: userDispatch}}>
                <div className="App">

                    <AppNavbar 
                        currentLang={locale.full}
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
            </UserContext.Provider>
        </HashRouter>
    );
}

export default App;
