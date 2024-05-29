'use client';
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./style/globals.css";
import dynamic from "next/dynamic";

const ReduxProvider = dynamic(() => import("@/redux/redux-provider"), {
  ssr: false
});

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {

  return (
    <html lang="en">
      <body className={inter.className}>
        <div className="mt-100">
        <ReduxProvider > {children}</ReduxProvider>
        </div>
      </body>
    </html>
  );
}