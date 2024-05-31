import { accessTokenAPI, pythonInstance } from "../../common/config/axios-config"
import { RQ } from "../../common/enum/RQ"

export const ChatAPI = (category:string, req: any) => {
    return accessTokenAPI(pythonInstance(), "post", `${RQ.CHAT}/${category.replace(" ", "-").toLowerCase()}`, {}, req)
}