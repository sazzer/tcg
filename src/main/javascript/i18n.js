import i18n from 'i18next';
import XHR from 'i18next-xhr-backend';
import LanguageDetector from 'i18next-browser-languagedetector';

i18n
    .use(XHR)
    .use(LanguageDetector)
    .init({
        backend: {
            loadPath: '/locales/{{ns}}/{{lng}}.json'
        },

        // have a common namespace used around the full app
        ns: ['tcg'],
        defaultNS: 'tcg',

        debug: true,

        interpolation: {
            escapeValue: false
        }
    });

export default i18n;
