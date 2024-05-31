import { UseFormSetValue } from "react-hook-form";

export default function ToeicRadioButton({
  title,
  target,
  setValue,
}: {
  title: string;
  target: string;
  setValue: UseFormSetValue<any>;
}) {
  return (
    <li>
      <input
        type="radio"
        id={title}
        onChange={() => setValue(target, title)}
        name={target}
        className="hidden peer"
      />
      <label
        htmlFor={title}
        className="inline-flex items-center justify-between w-full p-5 text-gray-500 bg-white border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-600 hover:bg-gray-100 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
      >
        <div className="w-full block">
          <div className="text-lg font-semibold">{title}</div>
        </div>
        <img src="/right_arrow.svg" className="w-5 h-5 ms-3 rtl:rotate-180" />
      </label>
    </li>
  );
}
