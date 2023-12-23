import { Instant } from 'domain/datetime';
import { UserId } from 'generated/domain/Ids.generated';

export type UserStatus = 'MailValidationPending' | 'Active' | 'Disabled';

export type Role = 'User' | 'Admin';

export interface UserInfos {
  id: UserId;
  mail: string;
  displayName: string;
  status: UserStatus;
  role: Role;
}

export interface AdminUserInfos {
  id: UserId;
  mail: string;
  displayName: string;
  status: UserStatus;
  role: Role;
  signupDate: Instant;
}

export type LoginResult = 'LoggedIn' | 'MailNotFound' | 'BadPassword';

export type RegisterResult = 'Registered' | 'MailAlreadyExists';

export type UpdatePasswordResult = 'BadPassword' | 'PasswordChanged';
