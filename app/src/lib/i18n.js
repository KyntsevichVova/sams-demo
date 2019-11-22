import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import XHR from 'i18next-xhr-backend';

i18n
    .use(XHR)
    .use(initReactI18next)
    .init({
        lng: 'en',
        fallbackLng: 'en',
        load: 'languageOnly',

        backend: {
            loadPath: `/demo/resources/{{lng}}/{{ns}}.json`
        }
    });

export default i18n;