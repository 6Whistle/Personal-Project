import { UseFormRegister } from "react-hook-form";
import TextInput from "../../../atomic/input/text-input";
import ImageButton, {
  buttonImagesUrl,
} from "../../../atomic/button/image-button";
import { Toeic } from "../model/toeic";


export default function ToeicInputForm({
  register,
  answer,
  userAnswer,
}: {
  register: UseFormRegister<Toeic>;
  answer: string;
    userAnswer: string;
}) {
    const onClickHandler = ({target: {value}}: any) => {
        !answer 
        ? alert("Please generate question first")
        : answer === userAnswer
        ? alert("Correct")
        : alert("Incorrect")
    }

  return (
    <div className="w-5/6 h-10 px-4 py-2 bg-white rounded-lg border border-neutral-200 justify-start items-center gap-3 flex">
      <TextInput register={register} target="userAnswer" />
      <div className="w-[80px] h-8 px-6 py-3.5 bg-black rounded-lg shadow justify-center items-center gap-2 flex">
        <button
          type="submit"
          className="text-white text-base font-medium font-['Inter'] leading-normal"
        >
          Generate
        </button>
      </div>
      <div className="w-[60px] h-8 px-6 py-3.5 bg-black rounded-lg shadow justify-center items-center gap-2 flex">
        <button
            onClick={onClickHandler}
          className="text-white text-base font-medium font-['Inter'] leading-normal"
        >
          Check
        </button>
      </div>
    </div>
  );
}
