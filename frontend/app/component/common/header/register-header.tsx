import Link from "next/link";
import HeaderButton from "../../../atomic/header/header-button";
import TabButton, { loginedTabNames, tabNames } from "../../../atomic/header/tab-button";
import { PG } from "../enum/PG";
import { RQ } from "../enum/RQ";
import { parseCookies } from "nookies";
import HeaderLogoutButton from "@/app/atomic/header/header-logout-button";

export default function RegisterHeader() {
  return (
    <nav className="w-full h-16 px-20 py-4 bg-white shadow border-b-2 border-neutral-200 justify-start items-center flex">
      <Link className="text-black text-xl font-semibold font-['Inter'] whitespace-nowrap" href={"/"}>
        6Whistle Blog
      </Link>
      <div className="w-full justify-center items-center gap-8 flex">
        {
          parseCookies().refreshToken
          ? loginedTabNames.map((i, index) =>  <TabButton tabName={i.tabName} key={index} url={i.url} />)
          : tabNames.map((i, index) => <TabButton tabName={i.tabName} key={index} url={i.url} />)
        }
      </div>
      <div className="justify-start items-start gap-3 flex">
        <div className="w-9 justify-center items-center gap-2 flex">
          <img src="/profile.svg" alt="profileImg" />
        </div>
        {
          parseCookies().refreshToken 
          ? <HeaderLogoutButton text="Logout" />
          : <HeaderButton text="Register" url={`${PG.USER}${RQ.REGISTER}`} />
        }

      </div>
    </nav>
  );
}
