import { createSlice } from "@reduxjs/toolkit";
import { User } from "../model/user";
import { checkEmail, loginUser, registerUser, updateUser } from "./user-service";

const status = {
    pending: "pending",
    fulfilled: "fulfilled",
    rejected: "rejected"
}

const initialState = {
    array: [] as User[],
    json: {} as User,
    status: false as boolean,
    id: 0 as number,
}

export const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        setUserArray: (state, {payload}) => { state.array = payload },
        setUserJson: (state, {payload}) => { state.json = payload },
        setUserStatus: (state, {payload}) => { state.status = payload },
        setUserId: (state, {payload}) => { state.id = payload },
    },
    extraReducers: builder => {
        const {pending, rejected} = status;
        builder
        .addCase(checkEmail.fulfilled, (state:any, {payload}: any) => { state.status = (payload.status === 200) })
        .addCase(registerUser.fulfilled, (state:any, {payload}:any) => { state.status = (payload.status === 200) })
        .addCase(loginUser.fulfilled, (state:any, {payload}:any) => { state.status = (payload.status === 200) })
        .addCase(updateUser.fulfilled, (state:any, {payload}:any) => { state.status = (payload.status === 200) })
    }
})

export const getUserStatus = (state: any) => state.user.status;
export const getUserId = (state: any) => state.user.id;
export const getUserArray = (state: any) => state.user.array;
export const getUserJson = (state: any) => state.user.json;

export const { setUserArray, setUserJson, setUserStatus, setUserId } = userSlice.actions;

export default userSlice.reducer;