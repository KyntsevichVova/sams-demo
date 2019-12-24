import React from 'react';
import { useTranslation } from 'react-i18next';
import UserContext from '../../contexts/UserContext';
import flag_russia from '../../img/flag_russia.png';
import flag_uk from '../../img/flag_uk.png';
import { LOCALE } from '../../lib/Constraints';

function AppNavbar({ currentLang, changeLangCallback }) {
    const { userState, userDispatch } = React.useContext(UserContext);

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
                            className={`cursor-pointer${currentLang !== LOCALE.RU.short ? " opaque-5" : ""}`}
                            alt={ LOCALE.RU.full } 
                            onClick={ ()=>{changeLangCallback(LOCALE.RU)} }
                            height="32"
                            width="32"
                        />
                        <img
                            src={ flag_uk } 
                            className={`cursor-pointer${currentLang !== LOCALE.EN.short ? " opaque-5" : ""}`}
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
                                        {`Hello, ${userState.username}`}
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
                                            userDispatch({type: 'signin', username: "placeholder"});
                                        }}
                                    >
                                        { t('nav.signin') }
                                    </button>
                                    <button 
                                        className="btn btn-primary border border-white ml-1"
                                        onClick={() => {
                                            userDispatch({type: 'signup', username: "placeholder"});
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
        </nav>
    );
}

export default AppNavbar;