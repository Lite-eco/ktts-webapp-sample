import { UserId } from '../domain/Ids.generated';
import { PlainStringPassword } from '../domain/Security.generated';
import {
  LoginResult,
  RegisterResult,
  Role,
  UserInfos,
  UserStatus
} from '../domain/User.generated';
import { UserAccountOperationToken } from '../domain/UserAccountOperationToken.generated';

export type Command =
  | AdminUpdateRoleCommand
  | AdminUpdateSessions
  | AdminUpdateStatusCommand
  | DevLoginCommand
  | LoginCommand
  | RegisterCommand
  | UpdatePasswordCommand
  | ValidateMailCommand;

export type CommandResponse =
  | DevLoginCommandResponse
  | EmptyCommandResponse
  | LoginCommandResponse
  | RegisterCommandResponse;

export interface EmptyCommandResponse {
  objectType: 'EmptyCommandResponse';
}

export interface AdminUpdateRoleCommand {
  objectType: 'AdminUpdateRoleCommand';
  userId: UserId;
  role: Role;
}

export interface AdminUpdateSessions {
  objectType: 'AdminUpdateSessions';
}

export interface AdminUpdateStatusCommand {
  objectType: 'AdminUpdateStatusCommand';
  userId: UserId;
  status: UserStatus;
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

export interface UpdatePasswordCommand {
  objectType: 'UpdatePasswordCommand';
  password: PlainStringPassword;
}

export interface ValidateMailCommand {
  objectType: 'ValidateMailCommand';
  token: UserAccountOperationToken;
}
