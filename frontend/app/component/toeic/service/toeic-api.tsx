import { accessTokenAPI, springInstance } from "../../common/config/axios-config";
import { API } from "../../common/enum/API";
import { RQ } from "../../common/enum/RQ";

export const getRandomQuestionAPI = async () => 
    accessTokenAPI(springInstance(), "get", `${API.TOEIC}${RQ.RANDOM}`, {}, {});