export default function RegisterImage() {
  return (
    <div className="flex-1 bg-black text-center hidden md:flex">
      <div
        className="m-12 xl:m-16 w-full bg-contain bg-center bg-no-repeat"
        style={{
          backgroundImage: `url(https://www.tailwindtap.com/assets/common/marketing.svg)`,
        }}
      ></div>
    </div>
  );
}
