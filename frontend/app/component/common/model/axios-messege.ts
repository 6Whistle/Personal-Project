export interface Messenger {
    id: number;
    message : string;
    status: number;
    accessToken: string;
    refreshToken: string;
    values: any;
}