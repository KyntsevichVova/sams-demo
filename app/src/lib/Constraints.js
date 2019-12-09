export const BASE_SUBDIR = "demo";
export const BASE_URL = `/${BASE_SUBDIR}`;
export const API_URL = `${BASE_URL}/api/v1`;
export const QUESTIONS_ENDPOINT = `${API_URL}/questions`;

export const FILTERS = [
    {filter: "all", text: "all"},
    {filter: "JUNIOR", text: "junior"},
    {filter: "MIDDLE", text: "middle"},
    {filter: "SENIOR", text: "senior"},
];

export const PAGE_SIZES = [5, 10, 25, 50];

export const LOCALE = {
    EN: {
        full: 'en-US',
        short: 'en'
    },
    RU: {
        full: 'ru-RU',
        short: 'ru'
    }
};