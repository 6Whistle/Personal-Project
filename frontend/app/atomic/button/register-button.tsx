import RegisterSvg from "@/public/register_icon.svg";

export default function RegisterButton() {
  return (
    <button 
    type="submit"
    className="mt-5 tracking-wide font-semibold bg-gray-600 text-gray-100 w-full py-4 rounded-lg hover:bg-black transition-all duration-300 ease-in-out flex items-center justify-center focus:shadow-outline focus:outline-none">
      <RegisterSvg className="w-6 h-6 -ml-2" />
      <span className="ml-3">Sign Up</span>
    </button>
  );
}
