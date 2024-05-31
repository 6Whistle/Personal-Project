'use client'

import ToeicRadioButton from "@/app/atomic/button/toeic-radio-button"
import RegisterHeader from "@/app/component/common/header/register-header"
import { Toeic } from "@/app/component/toeic/model/toeic"
import ToeicInputForm from "@/app/component/toeic/module/toeic-input-form"
import { getRandomQuestionAPI } from "@/app/component/toeic/service/toeic-api"
import { useRouter } from "next/navigation"
import { parseCookies } from "nookies"
import { useEffect } from "react"
import { SubmitHandler, useForm } from "react-hook-form"

export default function ToeicPage(){
    const router = useRouter()
    const { register, handleSubmit, watch, setValue, reset, formState: { errors } } = useForm<Toeic>()
    

    useEffect(() => {
        if(!parseCookies().refreshToken) router.replace(`/`)
    }, [])

    const onSubmit: SubmitHandler<Toeic> = async () => {
        reset((await getRandomQuestionAPI()).values)
    }

    return(
        <div className="w-full h-full bg-white flex-col justify-center items-center flex">
        <RegisterHeader />
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="w-full h-full p-6 bg-white flex-col justify-center items-center gap-8 flex"
        >
    <div className="w-5/6 h-[500px] px-10 py-5 flex-col bg-zinc-100 justify-start items-start gap-6 flex rounded-lg">
      <div className="self-stretch text-black text-[64px] font-bold font-['Inter']">
        TOEIC test
      </div>
      <div className="py-5 self-stretch text-zinc-500 text-2xl font-normal font-['Inter'] leading-9">
        {watch("question")}
        </div>
        <ul className='w-5/6 grid gap-6 md:grid-cols-2'>
          {watch("choices")?.map((radioButton, i) => (
            <ToeicRadioButton
              key={i}
              title={radioButton}
              target="userAnswer"
              setValue={setValue}
            />
          ))}
        </ul>
    </div>
          {<ToeicInputForm register={register} answer={watch("answer")} userAnswer={watch("userAnswer")} />}
        </form>
      </div>
    )
}