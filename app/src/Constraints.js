export const BASE_SUBDIR = "demo";
export const BASE_URL = `http://localhost:8085/${BASE_SUBDIR}`;
export const API_URL = `${BASE_URL}/api/v1`;
export const QUESTIONS_ENPOINT = `${API_URL}/questions`;

export const FILTERS = [
    {filter: "all", text: "All"},
    {filter: "junior", text: "Junior"},
    {filter: "middle", text: "Middle"},
    {filter: "senior", text: "Senior"},
];

export const PAGE_SIZES = [5, 10, 25, 50];
