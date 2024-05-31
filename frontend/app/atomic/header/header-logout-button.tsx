import { PG } from "@/app/component/common/enum/PG";
import { logoutUser } from "@/app/component/user/service/user-service";
import { useRouter } from "next/navigation";
import { useDispatch } from "react-redux";

export default function HeaderLogoutButton({ text }: { text: string }) {
    const router = useRouter();
    const dispatch = useDispatch();
    const logoutHandler = () => {
        dispatch(logoutUser())
        router.push(`/`)
    }

    return (
        <button onClick={logoutHandler} className="w-24 px-4 bg-gray-500 hover:bg-black duration-300 rounded-lg justify-center items-center gap-2 flex">
        <div className="text-white text-base font-medium font-['Inter'] leading-[35px]">
          {text}
        </div>
      </button>
    )
}

