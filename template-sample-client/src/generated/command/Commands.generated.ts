import { UserId } from 'generated/domain/Ids.generated';
import { PlainStringPassword } from 'generated/domain/Security.generated';
import {
  LoginResult,
  RegisterResult,
  Role,
  UpdatePasswordResult,
  UserInfos,
  UserStatus
} from 'generated/domain/User.generated';
import { UserAccountOperationToken } from 'generated/domain/UserAccountOperationToken.generated';

export type Command =
  | AdminUpdateRoleCommand
  | AdminUpdateSessionsCommand
  | AdminUpdateStatusCommand
  | AdminUpdateUserMailCommand
  | DevLoginCommand
  | LoginCommand
  | RegisterCommand
  | ResetLostPasswordCommand
  | SendLostPasswordMailCommand
  | UpdatePasswordCommand
  | ValidateMailCommand;

export type CommandResponse =
  | DevLoginCommandResponse
  | EmptyCommandResponse
  | LoginCommandResponse
  | RegisterCommandResponse
  | ResetLostPasswordCommandResponse
  | UpdatePasswordCommandResponse;

export interface EmptyCommandResponse {
  objectType: 'EmptyCommandResponse';
}

export interface AdminUpdateRoleCommand {
  objectType: 'AdminUpdateRoleCommand';
  userId: UserId;
  role: Role;
}

export interface AdminUpdateSessionsCommand {
  objectType: 'AdminUpdateSessionsCommand';
}

export interface AdminUpdateStatusCommand {
  objectType: 'AdminUpdateStatusCommand';
  userId: UserId;
  status: UserStatus;
}

export interface AdminUpdateUserMailCommand {
  objectType: 'AdminUpdateUserMailCommand';
  userId: UserId;
  mail: string;
}

export interface DevLoginCommand {
  objectType: 'DevLoginCommand';
  username: string;
}

export interface DevLoginCommandResponse {
  objectType: 'DevLoginCommandResponse';
  userInfos: UserInfos;
}

export interface LoginCommand {
  objectType: 'LoginCommand';
  mail: string;
  password: PlainStringPassword;
}

export interface LoginCommandResponse {
  objectType: 'LoginCommandResponse';
  result: LoginResult;
  userInfos?: UserInfos;
}

export interface RegisterCommand {
  objectType: 'RegisterCommand';
  mail: string;
  password: PlainStringPassword;
  displayName: string;
}

export interface RegisterCommandResponse {
  objectType: 'RegisterCommandResponse';
  result: RegisterResult;
  userInfos?: UserInfos;
}

export interface ResetLostPasswordCommand {
  objectType: 'ResetLostPasswordCommand';
  token: UserAccountOperationToken;
  newPassword: PlainStringPassword;
}

export interface ResetLostPasswordCommandResponse {
  objectType: 'ResetLostPasswordCommandResponse';
  userInfos: UserInfos;
}

export interface SendLostPasswordMailCommand {
  objectType: 'SendLostPasswordMailCommand';
  mail: string;
}

export interface UpdatePasswordCommand {
  objectType: 'UpdatePasswordCommand';
  currentPassword: PlainStringPassword;
  newPassword: PlainStringPassword;
}

export interface UpdatePasswordCommandResponse {
  objectType: 'UpdatePasswordCommandResponse';
  result: UpdatePasswordResult;
}

export interface ValidateMailCommand {
  objectType: 'ValidateMailCommand';
  token: UserAccountOperationToken;
}
