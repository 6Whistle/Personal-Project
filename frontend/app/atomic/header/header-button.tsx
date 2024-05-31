import Link from "next/link";
import { useRouter } from "next/router";

export default function HeaderButton({ text, url }: { text: string, url: string}) {
  return (
    <Link href={url}>
    <button className="w-24 px-4 bg-gray-500 hover:bg-black duration-300 rounded-lg justify-center items-center gap-2 flex">
      <div className="text-white text-base font-medium font-['Inter'] leading-[35px]">
        {text}
      </div>
    </button>
    </Link>
  );
}
