'use client';

import Link from "next/link";
import RegisterHeader from "./component/common/header/register-header";
import { LoginImage } from "./atomic/image/login-image";
import { User } from "./component/user/model/user";
import { useForm } from "react-hook-form";
import { PG } from "./component/common/enum/PG";
import { RQ } from "./component/common/enum/RQ";
import LoginButton from "./atomic/button/login-button";
import LoginInput, { loginList } from "./atomic/input/login-input";
import SignUpButton from "./atomic/button/sign-up-button";
import { useDispatch } from "react-redux";
import { checkEmail, loginUser } from "./component/user/service/user-service";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { parseCookies } from "nookies";

export default function Home() {
  const router = useRouter();  
  const dispatch = useDispatch();

    const { register, handleSubmit, watch, setValue, formState: { errors } } = useForm<User>();
    const onSubmit = (data: User) => {
        dispatch(checkEmail(data.email))
        .then(({payload}: any) => {
          console.log(payload)
          payload.status === 200
          ? dispatch(loginUser(data))
          .then(({payload}: any) => {
            console.log(payload)
            if(payload.status === 200){
              console.log("Login success")
              alert("Login success")
              router.push(`${PG.CHAT}`)
            }
            else alert("Login failed");
          })
          : alert("Email not found");
        })
    };

    const onInValid = () => {
        alert("Please fill in the form!");
    }

    useEffect(() => {
      if (parseCookies().refreshToken) router.replace(`${PG.CHAT}`)
    }, [])

    return (
        <div className='w-full h-full bg-white flex-col justify-center items-center flex'>
        <RegisterHeader />
        <div className="h-[80vh] w-full items-center flex justify-center px-5 lg:px-0">
        <div className="flex items-center justify-center w-full px-5 sm:px-0">
          <div className="flex bg-white rounded-lg shadow-lg border overflow-hidden max-w-sm lg:max-w-4xl w-full">
            {<LoginImage />}
            <form className="w-full p-8 lg:w-1/2" onSubmit={handleSubmit(onSubmit, onInValid)}>
              <p className="text-xl text-gray-600 text-center">Welcome back!</p>
              {loginList.map((login, index) => (<LoginInput key={index} title={login.title} type={login.type} target={login.target} register={register} options={login.options} />))}
              <LoginButton />
              <SignUpButton url={`${PG.USER}${RQ.REGISTER}`} /> 
            </form>
          </div>
        </div>
        </div>
      </div>
    );
}