import { UseFormRegister } from "react-hook-form";

export default function RegisterInput({
  inputType,
  placeholder,
  target,
  register,
  options,
}: {
  inputType: string;
  placeholder: string;
  target: string;
  register: UseFormRegister<any>;
  options?: any;
}) {
  return (
    <input
      {...register(target, options)}
      className="w-full px-5 py-3 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-500 text-sm focus:outline-none focus:border-gray-400 focus:bg-white"
      type={inputType}
      placeholder={placeholder}
    />
  );
}

export const registerList: {
  inputType: string;
  target: string;
  placeholder: string;
  options: any;
}[] = [
  {
    inputType: "email",
    target: "email",
    placeholder: "Enter your email",
    options: { required: { value : true, message: "Email is required"}},
  },
  {
    inputType: "password",
    target: "password",
    placeholder: "Enter your Password",
    options: { required: { value : true, message: "Password is required"},
      minLength: {
        value: 6,
        message: "Password must be at least 6 characters",
      },
      maxLength: {
        value: 20,
        message: "Password must be at most 20 characters",
      },
      pattern: {
        value: /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$/,
        message: "Password must have at least one letter and one number",
      },
    },
  },
  {
    inputType: "text",
    target: "name",
    placeholder: "Enter your name",
    options: { required: { value : true, message: "Name is required"}},
  },
  {
    inputType: "date",
    target: "birth",
    placeholder: "Enter your birthdate",
    options: { required: { value : true, message: "Birth is required"}},
  },
  {
    inputType: "tel",
    target: "phone",
    placeholder: "Enter your phone",
    options: { required: { value : true, message: "Tel is required"}},
  },
];
