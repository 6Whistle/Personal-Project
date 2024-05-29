"use client";
import axios from "axios";
import {
  SubmitErrorHandler,
  SubmitHandler,
  useForm,
} from "react-hook-form";
import RadioButton, { radioButtonList } from "./atomic/button/radio-button";
import ChatInputForm from "./component/chat/module/chat-input-form";
import RegisterHeader from "./component/common/header/register-header";
import { ChatInput } from "./component/chat/model/chat";
import ChatOutputForm from "./component/chat/module/chat-output-form";

export default function Home() {
  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<ChatInput>();

  const onSubmit: SubmitHandler<ChatInput> = ({ question, category }) => {
    if (!category) {
      setValue("response", "Please choose a category");
      return;
    }
    setValue("response", "Loading...");
    axios
      .post(
        `http://localhost:8000/chat/${category
          .replace(" ", "-")
          .toLowerCase()}`,
        { question }
      )
      .then((res) => setValue("response", res.data.answer))
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
          title="BIT-LLM"
          subtitle="Ask everything to BIT-LLM"
          text={watch("response")}
        />
        <ul className="w-5/6 grid gap-6 md:grid-cols-3">
          {radioButtonList.map((radioButton, i) => (
            <RadioButton
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
