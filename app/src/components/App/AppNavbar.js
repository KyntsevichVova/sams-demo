import React from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Redirect } from 'react-router-dom';
import UserContext from '../../contexts/UserContext';
import flag_russia from '../../img/flag_russia.png';
import flag_uk from '../../img/flag_uk.png';
import { LOCALE } from '../../lib/Constraints';

function AppNavbar({ currentLang, changeLangCallback }) {
    const { userState, userDispatch } = React.useContext(UserContext);
    const [redirect, setRedirect] = React.useState({should: false, to: "/"});

    const { t } = useTranslation();

    return (
        <nav className="navbar navbar-dark mb-5 shadow-sm bg-primary border-bottom">
            <span className="navbar-brand" style={{fontSize: '2.5rem'}}>
                { t('main.title') }
            </span>
            <div className="d-flex flex-row justify-content-end">
                <div className="d-flex flex-column">
                    <div className="d-flex justify-content-end">
                        <img
                            src={ flag_russia } 
                            className={`cursor-pointer${currentLang !== LOCALE.RU.full ? " opaque-5" : ""}`}
                            alt={ LOCALE.RU.full } 
                            onClick={ ()=>{changeLangCallback(LOCALE.RU)} }
                            height="32"
                            width="32"
                        />
                        <img
                            src={ flag_uk } 
                            className={`cursor-pointer${currentLang !== LOCALE.EN.full ? " opaque-5" : ""}`}
                            alt={ LOCALE.EN.full } 
                            onClick={ ()=>{changeLangCallback(LOCALE.EN)} }
                            height="32"
                            width="32"
                        />
                    </div>
                    <div>
                        {userState.loggedIn 
                            ? (
                                <>
                                    <span className="text-white mr-1">
                                        <Trans i18nKey="nav.hello">
                                            Hello, {{username: userState.username}}
                                        </Trans>
                                    </span>
                                    <button 
                                        className="btn btn-primary border border-white ml-1"
                                        onClick={() => {
                                            userDispatch({type: 'signout'});
                                        }}
                                    >
                                        { t('nav.signout') }
                                    </button>
                                </>
                            ) 
                            : (
                                <>
                                    <button 
                                        className="btn btn-primary border border-white mr-1"
                                        onClick={() => {
                                            setRedirect({should: true, to: "/signin"});
                                        }}
                                    >
                                        { t('nav.signin') }
                                    </button>
                                    <button 
                                        className="btn btn-primary border border-white ml-1"
                                        onClick={() => {
                                            setRedirect({should: true, to: "/signup"});
                                        }}
                                    >
                                        { t('nav.signup') }
                                    </button>
                                </>
                            )
                        }
                    </div>
                </div>
            </div>
            {redirect.should && <Redirect to={redirect.to}/>}
        </nav>
    );
}

export default AppNavbar;