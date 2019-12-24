import React from 'react';
import { useTranslation } from 'react-i18next';
import flag_russia from '../../img/flag_russia.png';
import flag_uk from '../../img/flag_uk.png';
import { LOCALE } from '../../lib/Constraints';

function AppNavbar({ currentLang, changeLangCallback }) {
    const { t } = useTranslation();

    return (
        <nav className="navbar navbar-dark mb-5 shadow-sm bg-primary border-bottom">
            <span className="navbar-brand" style={{fontSize: '2.5rem'}}>
                { t('main.title') }
            </span>
            <div className="d-flex flex-row justify-content-end">
                <img 
                    src={ flag_russia } 
                    className={`cursor-pointer${currentLang !== LOCALE.RU.short ? " opaque-5" : ""}`}
                    alt={ LOCALE.RU.full } 
                    onClick={ ()=>{changeLangCallback(LOCALE.RU)} }
                    height="48"
                    width="48"
                />
                <img 
                    src={ flag_uk } 
                    className={`cursor-pointer${currentLang !== LOCALE.EN.short ? " opaque-5" : ""}`}
                    alt={ LOCALE.EN.full } 
                    onClick={ ()=>{changeLangCallback(LOCALE.EN)} }
                    height="48"
                    width="48"
                />
            </div>
        </nav>
    );
}

export default AppNavbar;