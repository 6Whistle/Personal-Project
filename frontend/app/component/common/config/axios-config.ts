import axios, { AxiosInstance } from "axios";
import { destroyCookie, parseCookies, setCookie } from "nookies";
import { API } from "../enum/API";
import { RQ } from "../enum/RQ";
import { Messenger } from "../model/axios-messege";

export const createInstance = (url?: string) =>
  axios.create({
    baseURL: url,
    headers: {
      "Cash-Control": "no-cache",
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
      Authorization: "Bearer ~blah",
    },
  });

export const accessTokenInterceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      config.headers.setAuthorization(
        `Bearer ${parseCookies().accessToken ? parseCookies().accessToken : ""}`
      );
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
  return instance;
};

export const refreshTokenInterceptors = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      config.headers.set(
        "Refresh-Token",
        `Bearer ${
          parseCookies().refreshToken ? parseCookies().refreshToken : ""
        }`
      );
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
  return instance;
};

export const springInstance = () =>
  accessTokenInterceptors(
    createInstance(process.env.NEXT_PUBLIC_SPRING_API_URL)
  );

export const refreshInstance = () =>
  refreshTokenInterceptors(
    createInstance(process.env.NEXT_PUBLIC_SPRING_API_URL)
  );

export const noTokenInstance = () =>
  createInstance(process.env.NEXT_PUBLIC_SPRING_API_URL);

export const pythonInstance = () =>
  accessTokenInterceptors(
    createInstance(process.env.NEXT_PUBLIC_PYTHON_API_URL)
  );

export const refreshTokenAPI = async (): Promise<boolean> => {
  return parseCookies().refreshToken
    ? await refreshInstance()
        .get(`${API.USER}${RQ.REFRESH}`)
        .then((res) => {
          setCookie({}, "accessToken", res.data.accessToken, {
            httpOnly: false,
            path: "/",
            maxAge: 60 * 5,
          });
          return true;
        })
        .catch((error) => {
          console.log("refresh token error : ", error);
          destroyCookie({}, "accessToken", { path: "/" });
          destroyCookie({}, "refreshToken", { path: "/" });
          return false;
        })
    : false;
};

export const accessTokenAPI = async (
  instance: AxiosInstance,
  restMethod: string,
  url: string,
  params: any,
  body: any
): Promise<Messenger> => {
  return instance
    .request({ url: url, method: restMethod, params: params, data: body })
    .then((res) =>  res.data)
    .catch(async (error) => {
        console.log("error occured : ", error);
        if (error.response.data.status === 401)
            return (await refreshTokenAPI()) === true
              ? await accessTokenAPI(instance, restMethod, url, params, body)
              : { status: 400, message: "Failed" };
        return { status: 400, message: "Failed" };
    });
};
