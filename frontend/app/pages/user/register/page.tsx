"use client";

import RegisterButton from "@/app/atomic/button/register-button";
import RegisterImage from "@/app/atomic/image/register-image";
import RegisterInput, { registerList } from "@/app/atomic/input/register-input";
import { PG } from "@/app/component/common/enum/PG";
import RegisterHeader from "@/app/component/common/header/register-header";
import { User } from "@/app/component/user/model/user";
import {
  checkEmailAPI,
  findUserByIdAPI,
  loginUserAPI,
} from "@/app/component/user/service/user-api";
import { registerUser } from "@/app/component/user/service/user-service";
import {
  getUserJson,
  getUserStatus,
} from "@/app/component/user/service/user-slice";
import { log } from "console";
import { parseCookie } from "next/dist/compiled/@edge-runtime/cookies";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { parseCookies } from "nookies";
import { useEffect } from "react";
import { FieldErrors, SubmitHandler, useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";

export default function RegisterUser() {
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<User>();

  const user: User = useSelector(getUserJson);
  const status: boolean = useSelector(getUserStatus);

  const dispatch = useDispatch();

  const router = useRouter();

  const onSubmit: SubmitHandler<User> = (data) =>
    dispatch(registerUser(data))
      .then(({ payload }: any) => {
        alert(payload.status === 200 ? "Register success" : "Register failed");
        payload.status === 200
          ? router.push("/")
          : console.log("Register failed : ", payload.status);
      })
      .catch((error: any) => console.log("Register error : ", error));

    const onInValid = (errors: FieldErrors) => {
      console.log(errors)
        errors.email?.message
        ? alert(errors.email?.message)
        : errors.password?.message
        ? alert(errors.password?.message)
        : errors.name?.message
        ? alert(errors.name?.message)
        : errors.birth?.message
        ? alert(errors.birth?.message)
        : errors.phone?.message
        ? alert(errors.phone?.message)
        : alert("Please enter all fields")
    }

  useEffect(() => {
    if (parseCookies().refreshToken) router.replace(`${PG.CHAT}`)
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
                  Student Sign up
                </h1>
                <p className="text-[12px] text-gray-500">
                  Hey enter your details to create your account
                </p>
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
                  <RegisterButton title={"Sign in"} />
                  <p className="mt-6 text-xs text-gray-600 text-center">
                    Already have an account?{" "}
                    <Link href="/">
                      <span className="text-black font-semibold">Sign in</span>
                    </Link>
                  </p>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
