/**
 * Created by shruti.aggarwal on 23/3/16.
 */
export class LoginResponseModel {
  token: string;
  merchantId: string;
  userDTO: UserResponseModel;
}

export class UserResponseModel{
  userId:string;
  userName: string;
  emailId: string;
  loginName: string;
  roleList: Role[]
}

export class Role {
  id: number;
  name: string;
  displayName: string;
  enabled: boolean;
}
