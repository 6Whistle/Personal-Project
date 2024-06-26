import { PG } from "@/app/component/common/enum/PG";
import { RQ } from "@/app/component/common/enum/RQ";
import { url } from "inspector";
import Link from "next/link";

export default function TabButton({ tabName, url }: { tabName: string, url: string }) {
  return (
    <Link href={url}>
    <div className="text-black text-base font-medium font-['Inter'] leading-normal whitespace-nowrap">
      {tabName}
    </div>
    </Link>
  );
}

export const tabNames: {tabName: string, url: string}[] = [];

export const loginedTabNames = [
  { tabName : "CHAT", url : `${PG.CHAT}` },
  { tabName : "TOEIC", url : `${PG.TOEIC}` },
  { tabName : "PROFILE", url : `${PG.USER}${RQ.DETAIL}` },
]

