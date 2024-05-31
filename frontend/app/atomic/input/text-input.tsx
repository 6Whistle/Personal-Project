import { UseFormRegister } from "react-hook-form";

export default function TextInput({
  register,
  target,
}: {
  register: UseFormRegister<any>;
  target: string;
}) {
  return (
    <input
      type="text"
      {...register(target)}
      className="w-full h-7 text-zinc-500 text-base font-normal font-['Inter'] leading-normal"
    />
  );
}
