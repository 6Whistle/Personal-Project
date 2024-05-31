import { error } from "console";
import { UseFormRegister } from "react-hook-form";

export default function LoginInput({
  title,
  type,
  target,
  register,
  options,
}: {
  title: string;
  type: string;
  target: string;
  register: UseFormRegister<any>;
  options?: any;
}) {
  return (
    <div className="mt-4 flex flex-col justify-between">
      <label className="block text-gray-700 text-sm font-bold mb-2">
        {title}
      </label>
      <input
        {...register(target, options)}
        className="text-gray-700 border border-gray-300 rounded py-2 px-4 block w-full focus:outline-2 focus:outline-blue-700"
        type={type}
      />
    </div>
  );
}

export const loginList = [
  {
    title: "Email",
    type: "email",
    target: "email",
    options: { required: true },
  },
  {
    title: "Password",
    type: "password",
    target: "password",
    options: {
      required: true,
      minLength: {
        value: 6,
        message: "Password must be at least 6 characters",
      },
      maxLength: {
        value: 20,
        message: "Password must be at most 20 characters",
      },
      format: {
        value: /^[a-zA-Z0-9]{6,20}$/,
        message: "Password must be alphanumeric",
      }
    },
  },
];
