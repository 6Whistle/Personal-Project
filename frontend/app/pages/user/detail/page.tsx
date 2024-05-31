'use client';

import RegisterButton from "@/app/atomic/button/register-button";
import RegisterImage from "@/app/atomic/image/register-image";
import RegisterInput, { registerList } from "@/app/atomic/input/register-input";
import RegisterHeader from "@/app/component/common/header/register-header";
import { User } from "@/app/component/user/model/user";
import { findUserByIdAPI } from "@/app/component/user/service/user-api";
import { deleteUser, findUserById, updateUser } from "@/app/component/user/service/user-service";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "next/navigation";
import { parseCookies } from "nookies";
import { use, useEffect } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { useDispatch } from "react-redux";

export default function UserDetailPage() {
    
    const dispatch = useDispatch()
    const router = useRouter()
    const { register, handleSubmit, watch, setValue, reset, formState: { errors } } = useForm<User>()
    const onSubmit:SubmitHandler<User> = (data:User) => 
        dispatch(updateUser(data))
        .then((res:any) => 
            res.payload.status === 200
            ? alert("Update success")
            : alert("Update fail"))
        .catch((error:any) => alert("Update fail"))
        .finally(() => router.refresh())
    
    const onInValid = () => {
        errors.email?.message
        ? alert(errors.email?.message)
        : errors.password?.message
        ? alert(errors.password?.message)
        : errors.name?.message
        ? alert(errors.name?.message)
        : errors.phone?.message
        ? alert(errors.phone?.message)
        : errors.birth?.message
        ? alert(errors.birth?.message)
        : alert("Please enter all fields")
    }

    const deleteHandler = () => {
        dispatch(deleteUser(jwtDecode<any>(parseCookies().refreshToken).userId))
        .then((res:any) => 
            res.payload.status === 200
            ? alert("Delete success")
            : alert("Delete fail"))
        .catch((error:any) => alert("Delete fail"))
        .finally(() => router.push(`/`))
    }

    useEffect(() => {
        !parseCookies().refreshToken
        ? router.replace(`/`)
        : dispatch(findUserById(jwtDecode<any>(parseCookies().refreshToken).userId))
        .then((res:any) => reset(res.payload.values))
        .catch((error:any) => console.log("error : ", error))
    }, [])


    return (
        <div className="w-full h-full bg-white flex-col justify-center items-center flex">
        <RegisterHeader />
        <div className="h-[80vh] w-full items-center flex justify-center px-5 lg:px-0">
          <div className="max-w-screen-xl bg-white border shadow sm:rounded-lg flex justify-center flex-1">
            <RegisterImage />
            <div className="lg:w-1/2 xl:w-5/12 p-6 sm:p-12">
              <div className=" flex flex-col items-center">
                <div className="text-center">
                  <h1 className="text-2xl xl:text-4xl font-extrabold text-gray-900">
                    Update your information
                  </h1>
                </div>
                <form
                  onSubmit={handleSubmit(onSubmit, onInValid)}
                  className="w-full flex-1 mt-8"
                >
                <div className="mx-auto max-w-xs flex flex-col gap-4">
                    {registerList.map((item, index) => (
                        <RegisterInput
                            inputType={item.inputType}
                            placeholder={item.placeholder}
                            target={item.target}
                            register={register}
                            options={item.options}
                            key={index}
                        />
                    ))}
                    <RegisterButton title={"Update"} />
                    <button className="tracking-wide font-semibold bg-gray-600 text-gray-100 w-full py-4 rounded-lg hover:bg-black transition-all duration-300 ease-in-out flex items-center justify-center focus:shadow-outline focus:outline-none"
                    onClick={deleteHandler}
                    type="button">DELETE</button>
                </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
}
