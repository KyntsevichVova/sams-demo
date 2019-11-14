export const BASE_SUBDIR = "demo";
export const BASE_URL = `http://localhost:8085/${BASE_SUBDIR}`;
export const API_URL = `${BASE_URL}/api/v1`;
export const QUESTIONS_ENDPOINT = `${API_URL}/questions`;

export const FILTERS = [
    {filter: "all", text: "All"},
    {filter: "JUNIOR", text: "Junior"},
    {filter: "MIDDLE", text: "Middle"},
    {filter: "SENIOR", text: "Senior"},
];

export const PAGE_SIZES = [5, 10, 25, 50];
