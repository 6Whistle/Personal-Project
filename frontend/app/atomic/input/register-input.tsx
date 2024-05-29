import { UseFormRegister } from "react-hook-form";

export default function RegisterInput({
  inputType,
  placeholder,
  target,
  register,
}: {
  inputType: string;
  placeholder: string;
  target: string;
  register: UseFormRegister<any>;
}) {
  return (
    <input
      {...register(target, { required: true })}
      className="w-full px-5 py-3 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-500 text-sm focus:outline-none focus:border-gray-400 focus:bg-white"
      type={inputType}
      placeholder={placeholder}
    />
  );
}

export const registerList = [
  {
    inputType: "text",
    target: "name",
    placeholder: "Enter your name",
  },
  {
    inputType: "email",
    target: "email",
    placeholder: "Enter your email",
  },
  {
    inputType: "tel",
    target: "phone",
    placeholder: "Enter your phone",
  },
  {
    inputType: "password",
    target: "password",
    placeholder: "Enter your Password",
  },
  {
    inputType: "date",
    target: "birth",
    placeholder: "Enter your birthdate",
  },
  {
    inputType: "tel",
    target: "phone",
    placeholder: "Enter your phone",
  },
];
