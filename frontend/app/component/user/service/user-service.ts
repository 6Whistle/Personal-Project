import { createAsyncThunk } from "@reduxjs/toolkit";
import { checkEmailAPI, deleteUserAPI, findUserByIdAPI, loginUserAPI, logoutUserAPI, registerUserAPI, updateUserAPI } from "./user-api";
import { User } from "../model/user";

export const checkEmail:any = createAsyncThunk("user/checkEmail", async (email:string) => checkEmailAPI(email))

export const registerUser:any = createAsyncThunk("user/register", async (user: User) => registerUserAPI(user))

export const loginUser:any = createAsyncThunk("user/login", async (user: User) => loginUserAPI(user.email, user.password))

export const logoutUser:any = createAsyncThunk("user/logout", async () => logoutUserAPI())

export const updateUser:any = createAsyncThunk("user/update", async (user: User) => updateUserAPI(user))

export const findUserById:any = createAsyncThunk("user/findById", async (id: number) => findUserByIdAPI(id))

export const deleteUser:any = createAsyncThunk("user/delete", async (id: number) => deleteUserAPI(id))

