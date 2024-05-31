import { destroyCookie, parseCookies, setCookie } from "nookies";
import {
  accessTokenAPI,
  noTokenInstance,
  springInstance,
} from "../../common/config/axios-config";
import { API } from "../../common/enum/API";
import { RQ } from "../../common/enum/RQ";
import { User } from "../model/user";
import { jwtDecode } from "jwt-decode";

export const findUserByIdAPI = async (id: number) =>
  accessTokenAPI(springInstance(), "get", `${API.USER}${RQ.DETAIL}`, {id}, null);

export const checkEmailAPI = async (email: string) =>
  accessTokenAPI(
    springInstance(),
    "get",
    `${API.USER}${RQ.EMAIL}`,
    { email },
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

export const logoutUserAPI = async () => {
  const msg = parseCookies().refreshToken
    ? accessTokenAPI(
        springInstance(),
        "post",
        `${API.USER}${RQ.LOGOUT}`,
        null,
        { id: jwtDecode<any>(parseCookies().refreshToken).userId }
      )
    : new Promise<any>(() => {return { status: 200, message: "Logout success"} })
    destroyCookie({}, "accessToken", { path: "/" });
    destroyCookie({}, "refreshToken", { path: "/" });
  return msg
};

export const updateUserAPI = async (user: User) =>
  accessTokenAPI(
    springInstance(),
    "put",
    `${API.USER}${RQ.UPDATE}`,
    null,
    user
  );

export const deleteUserAPI = async (id: number) => 
  accessTokenAPI(springInstance(), "delete", `${API.USER}${RQ.DELETE}`, { id }, null)
  .then((res) => {
    if(res.status === 200){
      destroyCookie({}, "accessToken", { path: "/" });
      destroyCookie({}, "refreshToken", { path: "/" });
      return res;
    }
  });