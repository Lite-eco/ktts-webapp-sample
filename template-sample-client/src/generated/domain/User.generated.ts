import { UserId } from './Ids.generated';

export type UserStatus = 'MailValidationPending' | 'Active' | 'Disabled';

export type Role = 'User' | 'Admin';

export interface UserInfos {
  id: UserId;
  mail: string;
  displayName: string;
  status: UserStatus;
  role: Role;
}

export type LoginResult = 'LoggedIn' | 'MailNotFound' | 'BadPassword';

export type RegisterResult = 'Registered' | 'MailAlreadyExists';

export type UpdatePasswordResult = 'BadPassword' | 'PasswordChanged';
