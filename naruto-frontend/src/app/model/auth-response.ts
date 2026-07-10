import { Role } from "./role";

export interface AuthResponse {
    token: string,
    role: Role,
    equipeId: number,
    success: boolean,
}
