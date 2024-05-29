import { setCookie } from "nookies";
import {
  accessTokenAPI,
  noTokenInstance,
  springInstance,
} from "../../common/config/axios-config";
import { API } from "../../common/enum/API";
import { RQ } from "../../common/enum/RQ";
import { User } from "../model/user";

export const findUserByIdAPI = async (id: number) =>
  accessTokenAPI(springInstance(), "get", `${API.USER}${id}`, id, null);

export const checkEmailAPI = async (email: string) =>
  accessTokenAPI(
    springInstance(),
    "get",
    `${API.USER}${RQ.EMAIL}`,
    {email},
    null
  );

export const registerUserAPI = async (user: User) =>
  (await noTokenInstance().post(`${API.USER}${RQ.REGISTER}`, user)).data;

export const loginUserAPI = async (email: string, password: string) =>
  noTokenInstance()
    .post(`${API.USER}${RQ.LOGIN}`, { email, password })
    .then((res) => {
      setCookie({}, "accessToken", res.data.accessToken, {
        httpOnly: false,
        path: "/",
        maxAge: 60 * 5,
      });
      setCookie({}, "refreshToken", res.data.refreshToken, {
        httpOnly: false,
        path: "/",
        maxAge: 60 * 60 * 24,
      });
      return res.data;
    })
    .catch((error) => {
      console.log(error);
      return error;
    });

export const updateUserAPI = async (user: User) =>
  accessTokenAPI(
    springInstance(),
    "put",
    `${API.USER}${RQ.UPDATE}`,
    null,
    user
  );
