import Link from "next/link";

export default function HeaderButton({ text }: { text: string }) {
  return (
    <button className="w-24 px-4 bg-gray-500 hover:bg-black duration-300 rounded-lg justify-center items-center gap-2 flex">
      <Link className="text-white text-base font-medium font-['Inter'] leading-[35px]" href={"/pages/user/register"}>
        {text}
      </Link>
    </button>
  );
}
