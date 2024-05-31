"use client";
import axios from "axios";
import {
  SubmitErrorHandler,
  SubmitHandler,
  useForm,
} from "react-hook-form";
import ChatRadioButton, { radioButtonList } from "../../atomic/button/chat-radio-button";
import ChatInputForm from "../../component/chat/module/chat-input-form";
import RegisterHeader from "../../component/common/header/register-header";
import { ChatInput } from "../../component/chat/model/chat";
import ChatOutputForm from "../../component/chat/module/chat-output-form";
import { use, useEffect } from "react";
import { useRouter } from "next/navigation";
import { parseCookies } from "nookies";
import { ChatAPI } from "@/app/component/chat/service/chat-service";

export default function ChatPage() {
  const router = useRouter();

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<ChatInput>();

  useEffect(() => {
    if (!parseCookies().refreshToken) router.replace(`/ `)
  }, []);

  const onSubmit: SubmitHandler<ChatInput> = ({ question, category }) => {
    if (!category) {
      setValue("response", "Please choose a category");
      return;
    }
    setValue("response", "Loading...");
    ChatAPI(category, { question })
      .then((res) => setValue("response", res.values))
      .catch((error) => setValue("response", JSON.stringify(error)));
  };

  const onInValid: SubmitErrorHandler<ChatInput> = () => {
    setValue("response", "Please enter a question");
  };

  return (
    <div className="w-full h-full bg-white flex-col justify-center items-center flex">
      <RegisterHeader />
      <form
        onSubmit={handleSubmit(onSubmit, onInValid)}
        className="w-full h-full p-6 bg-white flex-col justify-center items-center gap-8 flex"
      >
        <ChatOutputForm
          title="CHATBOT"
          subtitle="Ask everything to chatbot"
          text={watch("response")}
        />
        <ul className='w-5/6 grid gap-6 md:grid-cols-2'>
          {radioButtonList.map((radioButton, i) => (
            <ChatRadioButton
              key={i}
              title={radioButton.title}
              subtitle={radioButton.subtitle}
              value={radioButton.value}
              target={radioButton.target}
              setValue={setValue}
            />
          ))}
        </ul>
        <ChatInputForm register={register} />
      </form>
    </div>
  );
}
