import Link from "next/link";

export default function SignUpButton({url} : {url: string}) {
  return (
    <div>
      <Link
        href={url}
        className=" flex items-center justify-center mt-4 text-white rounded-lg shadow-md hover:bg-gray-100"
      ></Link>
      <div className="mt-4 flex items-center w-full text-center">
        <Link
          href={url}
          className="text-xs text-gray-500 capitalize text-center w-full"
        >
          Don&apos;t have any account yet?
          <span className="text-blue-700"> Sign Up</span>
        </Link>
      </div>
    </div>
  );
}
